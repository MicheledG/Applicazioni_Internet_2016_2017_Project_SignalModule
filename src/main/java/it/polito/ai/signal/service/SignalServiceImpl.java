package it.polito.ai.signal.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.polito.ai.signal.model.Coordinates;
import it.polito.ai.signal.model.CreatedSignal;
import it.polito.ai.signal.model.Rate;
import it.polito.ai.signal.model.ReferencedSignal;
import it.polito.ai.signal.model.Signal;
import it.polito.ai.signal.repository.RateRepository;
import it.polito.ai.signal.repository.SignalRepository;

@Service
public class SignalServiceImpl implements SignalService {
	private static final String REMOTE_NICKNAME_ENDPOINT = "http://localhost:8083/profile/nickname";
	
	@Autowired
	SignalRepository signalRepository;
	
	@Autowired
	RateRepository rateRepository;

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
		/*Map<String, String> requestBody = new HashMap<>();
		requestBody.put("username", username);
		String nickname;
		
		// Send the profile creation request to the Profile module
		RestTemplate restTemplate = new RestTemplate();
		
		try {
			// POST request
			nickname = restTemplate.postForObject(
					REMOTE_NICKNAME_ENDPOINT,
					requestBody,
					String.class
			);
		} catch (Exception e) {
			// Error getting data from the Profile module
			System.err.println(e.getMessage());
			return false;
		}*/
		String nickname = "ciaoneproprio"; //mock nickname
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
		for (Iterator it = signals.iterator(); it.hasNext();) {
			Signal signal = (Signal) it.next();				
			signal.setAverage(computeAverage(signal));	//setting the right average
			//maybe signalRepository.save(signal) but it's not important
		}
		return signals;
	}

	private double computeAverage(Signal signal) {
		//computing average grade for each signal
		List<Rate> rates = rateRepository.findByCoordinates(signal.getCoordinates());
		
		int count = rates.size();
		double average = 0.0;
		if (count!=0) {			
			for (Iterator it = rates.iterator(); it.hasNext();) {
				average += ((Rate) it.next()).getRate();
			}
			average = average/count;
		}
		return average;
	}

	@Override
	public boolean addRate(Coordinates coordinates, String username, int rate) {
		/* Retrieving the document */ 
		Signal existing = signalRepository.findOneByCoordinates(coordinates);
		if (existing == null)
			return false;
		//updating last reference date
		existing.setLastAccess(new Date());
		signalRepository.save(existing);
		/* Check if a rate for this signal and from this username is provided yet */
		Rate existingRate = rateRepository.findOneByCoordinatesAndUsername(coordinates, username);
		if (existingRate == null) {
			/* All right, just insert a new rate document */
			existingRate = new Rate();
			existingRate.setCoordinates(coordinates);
			existingRate.setRate(rate);
			existingRate.setUsername(username);
			
			rateRepository.save(existingRate);	
			return true;
		}
		else {
			/* A rate from this username is provided yet, just update rate */
			existingRate.setRate(rate);
			
			rateRepository.save(existingRate);	
			return true;			
		}
	}	

}