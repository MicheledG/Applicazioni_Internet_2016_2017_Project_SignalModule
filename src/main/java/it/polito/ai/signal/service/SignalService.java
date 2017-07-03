package it.polito.ai.signal.service;

import java.util.List;

import it.polito.ai.signal.model.Coordinates;
import it.polito.ai.signal.model.CreatedSignal;
import it.polito.ai.signal.model.ReferencedSignal;
import it.polito.ai.signal.model.Signal;

public interface SignalService {
	public boolean exists(Coordinates coordinates);
	public void addSignal(Signal signal);
	public Signal getSignalByCoordinates(Coordinates coordinates);

	public boolean create(CreatedSignal signal, String username);
	public boolean updateSignal(ReferencedSignal signal);
	
	public List<Signal> getAll();
	public boolean cleanCollection();
	
	public boolean addRating(Coordinates coordinates, String username, int rating);

}
