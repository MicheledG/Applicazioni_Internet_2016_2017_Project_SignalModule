package it.polito.ai.signal.service;

import java.util.List;

import it.polito.ai.signal.model.Coordinates;
import it.polito.ai.signal.model.SignalDto;
import it.polito.ai.signal.model.ReferenceDto;
import it.polito.ai.signal.model.Signal;

public interface SignalService {
	
	/**
	 * Check if a signal identified by the given coordinates already exists
	 * 
	 * @param coordinates
	 * @return
	 */
	public boolean exists(Coordinates coordinates);

	/** 
	 * Create a new signal
	 * 
	 * @param signal
	 * @param username
	 * @return
	 */
	public boolean createSignal(SignalDto signal, String username);
	
	/** 
	 * Update a signal by updating its lastReferenceDate field.
	 * 
	 * @param signal
	 * @return
	 */
	public boolean updateSignal(ReferenceDto signal);
	
	/**
	 * Get all the signals.
	 * 
	 * @return
	 */
	public List<Signal> readSignals();
	
	/**
	 * Create a new rating for an existing signal.
	 * 
	 * @param coordinates
	 * @param username
	 * @param rating
	 * @return
	 */
	public boolean createRating(Coordinates coordinates, String username, int rating);
	
	/**
	 * Remove from the database all the ratings
	 * related to a signal that is not valid anymore.
	 */
	public void clearOldRatings();

}
