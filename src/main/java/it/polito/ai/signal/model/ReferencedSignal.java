package it.polito.ai.signal.model;

/*
 * Class incoming in the request body to REFERENCE an already created signal
 */


public class ReferencedSignal {
	
	private Coordinates coordinates;
	
	public ReferencedSignal(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

}
