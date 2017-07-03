package it.polito.ai.signal.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.polito.ai.signal.model.Coordinates;
import it.polito.ai.signal.model.Rating;
import it.polito.ai.signal.model.ReferenceDto;
import it.polito.ai.signal.model.Signal;
import it.polito.ai.signal.model.SignalDto;
import it.polito.ai.signal.repository.RatingRepository;
import it.polito.ai.signal.repository.SignalRepository;

@Service
public class SignalServiceImpl implements SignalService {
	
	private static final String REMOTE_NICKNAME_ENDPOINT = "http://localhost:8083/profile/nickname?username=";
	private static final int LIFETIME = 5*60*1000; // 5 minutes
	
	@Autowired
	SignalRepository signalRepository;
	
	@Autowired
	RatingRepository ratingRepository;

	@Override
	public boolean exists(Coordinates coordinates) {
		
		Signal signal = signalRepository.findOneByCoordinates(coordinates);
		
		if (signal == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean createSignal(SignalDto signalDto, String username) {
		
		// Define a template to perform rest requests
		RestTemplate restTemplate = new RestTemplate();
		
		// Get from the Profile module the nickname related to the current logged username
		String nickname;
		try {
			nickname = restTemplate.getForObject(REMOTE_NICKNAME_ENDPOINT + username, String.class);		
		} catch (Exception e) {
			// Something goes wrong with the REST request
			return false;
		}
		
		// Define and fill the signal to create
		Signal signal = new Signal();
		signal.setCoordinates(signalDto.getCoordinates());
		signal.setAuthor(nickname);
		Date creationDate = new Date();
		signal.setCreationDate(creationDate);
		signal.setLastReferenceDate(creationDate);
		signal.setAddress(signalDto.getAddress());
		signal.setDescription(signalDto.getDescription());
		signal.setAverage(0.0); // initalize the average rating to 0
		
		// Save the new signal onto the database
		signalRepository.save(signal);
		
		return true;
	}

	@Override
	public boolean updateSignal(ReferenceDto signalDto) {
		
		// Get the signal to update from the database
		Signal signal = signalRepository.findOneByCoordinates(signalDto.getCoordinates());
		
		// There is no signal to update
		if (signal == null) {
			return false;
		}
		
		// Update the lastReferenceDate field of the signal
		signal.setLastReferenceDate(new Date());
		signalRepository.save(signal);
		
		return true;
	}

	@Override
	public List<Signal> readSignals() {
		
		// Remove invalid signals from the database
		cleanCollection();

		// Return just the valid signals
		return signalRepository.findAll();
	}
	
	/**
	 * Remove invalid signals from the database.
	 * 
	 * @return
	 */
	private boolean cleanCollection() {
		
		List<Signal> signals = signalRepository.findAll();
		
		// Delete any signal that hasn't been referenced for more than LIFETIME milliseconds
		Date now = new Date();
		for (Signal s : signals) {
			if (now.getTime() - s.getLastReferenceDate().getTime() > LIFETIME) {
				signalRepository.delete(s);
			}
		}
		
		return true;
	}

	@Override
	public boolean createRating(Coordinates coordinates, String username, int ratingValue) {
		
		// Get the signal to rate
		Signal signal = signalRepository.findOneByCoordinates(coordinates);
		
		// No signal to rate
		if (signal == null) {
			return false;
		}
		
		// Update the last reference date
		signal.setLastReferenceDate(new Date());
		
		// Check if the current user has already rated this signal
		Rating rating = ratingRepository.findOneByCoordinatesAndUsername(coordinates, username);
		
		// First rating of the current user for this signal
		if (rating == null) {
			rating = new Rating();
			rating.setCoordinates(coordinates);
			rating.setRating(ratingValue);
			rating.setUsername(username);					
		} else {
			// Not the first rating of the current user for this signal, just replace the old rating
			rating.setRating(ratingValue);			
		}
		
		ratingRepository.save(rating);
		
		// Compute the new average of ratings about this signal
		signal.setAverage(computeAverage(signal));
		signalRepository.save(signal);
		
		return true;		
	}
	
	/**
	 * Compute the average value of the ratings about a signal.
	 * 
	 * @param signal
	 * @return
	 */
	private double computeAverage(Signal signal) {
		
		// Get all the ratings about the given signal
		List<Rating> ratings = ratingRepository.findByCoordinates(signal.getCoordinates());
		
		// Compute the average value
		int count = ratings.size();
		double average = 0.0;
		if (count!=0) {			
			for (Rating r : ratings) {
				average += r.getRating();
			}
			average = average/count;
		}
		
		return average;
	}

	@Override
	public void clearOldRatings() {
		
		List<Rating> ratings = ratingRepository.findAll();
		
		// Delete any rating related to a signal not valid
		for (Rating r : ratings) {
			if (signalRepository.findOneByCoordinates(r.getCoordinates()) == null) {
				ratingRepository.delete(r);
			}
		}
		
	}	

}
