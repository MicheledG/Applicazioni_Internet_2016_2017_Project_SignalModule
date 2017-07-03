package it.polito.ai.signal.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/*
 * A wrapper class to carry the rate
 */
public class RatingWrapper {
	
	@Min(1)
	@Max(5)
	private int rating;
	
	private String lat;
	private String lng;
	
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
}
