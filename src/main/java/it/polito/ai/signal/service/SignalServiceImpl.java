package it.polito.ai.signal.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.polito.ai.signal.model.Coordinates;
import it.polito.ai.signal.model.CreatedSignal;
import it.polito.ai.signal.model.Rating;
import it.polito.ai.signal.model.ReferencedSignal;
import it.polito.ai.signal.model.Signal;
import it.polito.ai.signal.repository.RatingRepository;
import it.polito.ai.signal.repository.SignalRepository;

@Service
public class SignalServiceImpl implements SignalService {
	private static final String REMOTE_NICKNAME_ENDPOINT = "http://localhost:8083/profile/nickname?username=";
	private static final int CLEAR_TIME = 60*1000;
	
	@Autowired
	SignalRepository signalRepository;
	
	@Autowired
	RatingRepository ratingRepository;

	@Override
	public boolean exists(Coordinates coordinates) {
		Signal existing = signalRepository.findOneByCoordinates(coordinates);
		if (existing == null) {
			return false;
		}		
		return true;
	}

	@Override
	public void addSignal(Signal signal) {
		signalRepository.save(signal);
		return;		
	}

	@Override
	public Signal getSignalByCoordinates(Coordinates coordinates) {
		return signalRepository.findOneByCoordinates(coordinates);
	}

	@Override
	public boolean create(CreatedSignal signal, String username) {
		String nickname;
		
		// ask the profile module to get the nickname from username
		RestTemplate restTemplate = new RestTemplate();
		
		try {
			// GET request
			nickname = restTemplate.getForObject(REMOTE_NICKNAME_ENDPOINT+username, String.class);
					
		} catch (Exception e) {
			// Error getting data from the Profile module
			System.err.println(e.getMessage());
			return false;
		}
		Signal created = new Signal();
		//setting coordinates
		created.setCoordinates(signal.getCoordinates());
		//setting the signal's author
		created.setAuthor(nickname);
		//setting timestamps
		Date creationDate = new Date();
		created.setOrigin(creationDate);
		created.setLastAccess(creationDate);
		//setting address
		created.setAddress(signal.getAddress());
		//setting description
		created.setDescription(signal.getDescription());
		//setting average to 0.0
		created.setAverage(0.0);
		
		
		signalRepository.save(created);
		return true;
	}

	@Override
	public boolean updateSignal(ReferencedSignal signal) {
		Signal existing = signalRepository.findOneByCoordinates(signal.getCoordinates());
		if (existing == null)
			return false;
		existing.setLastAccess(new Date());
		signalRepository.save(existing);
		return true;
	}

	@Override
	public List<Signal> getAll() {
		List<Signal> signals = signalRepository.findAll();
		if (signals==null)
			return null;
		for (Iterator<Signal> it = signals.iterator(); it.hasNext();) {
			Signal signal = it.next();	
			//TODO add age check here
		}
		return signals;
	}

	private double computeAverage(Signal signal) {
		//computing average grade for each signal
		List<Rating> rates = ratingRepository.findByCoordinates(signal.getCoordinates());
		
		int count = rates.size();
		double average = 0.0;
		if (count!=0) {			
			for (Iterator<Rating> it = rates.iterator(); it.hasNext();) {
				average += it.next().getRating();
			}
			average = average/count;
		}
		return average;
	}

	@Override
	public boolean addRating(Coordinates coordinates, String username, int rating) {
		/* Retrieving the document */ 
		Signal existing = signalRepository.findOneByCoordinates(coordinates);
		if (existing == null)
			return false;
		//updating last reference date
		existing.setLastAccess(new Date());
		/* Check if a rate for this signal and from this username is provided yet */
		Rating existingRating = ratingRepository.findOneByCoordinatesAndUsername(coordinates, username);
		if (existingRating == null) {
			/* All right, just insert a new rate document */
			existingRating = new Rating();
			existingRating.setCoordinates(coordinates);
			existingRating.setRate(rating);
			existingRating.setUsername(username);					
		}
		else {
			/* A rate from this username is provided yet, just update rating */
			System.err.println("arrivo a settare il rate");
			existingRating.setRate(rating);
			
	
		}
		ratingRepository.save(existingRating);
		/* Let's compute now the new average */
		existing.setAverage(computeAverage(existing));
		signalRepository.save(existing);
		
		return true;		
	}

	@Override
	public boolean cleanCollection() {
		List<Signal> signals = signalRepository.findAll();
		Date now = new Date();
		for (Iterator<Signal> it = signals.iterator(); it.hasNext();) {
			Signal s = it.next();
			if (now.getTime()-s.getLastAccess().getTime()>CLEAR_TIME) {
				signalRepository.delete(s);
			}
		}
		return true;
	}	

}
