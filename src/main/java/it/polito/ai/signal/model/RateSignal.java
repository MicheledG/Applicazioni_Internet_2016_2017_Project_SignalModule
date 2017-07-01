package it.polito.ai.signal.model;


//UNUSED
/*
 * Class to fill in the request body to VOTE a signal
 */

public class RateSignal {

	private Coordinates coordinates;
	
	private int rating;

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}
