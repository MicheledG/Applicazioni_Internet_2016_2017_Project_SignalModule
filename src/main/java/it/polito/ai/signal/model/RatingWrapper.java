package it.polito.ai.signal.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/*
 * A wrapper class to carry the rate
 */
public class RatingWrapper {
	
	@Min(1)
	@Max(5)
	int rating;
	
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}
