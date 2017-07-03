package it.polito.ai.signal.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

/**
 * A DTO for rating data
 */
public class RatingDto {
	
	@Min(1)
	@Max(5)
	@NotNull
	private int rating;
	
	@NotNull
	@Range(min = -90, max = 90)
	private double latitude;

	@NotNull
	@Range(min = -180, max = 180)
	private double longitude;
	
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLng(double longitude) {
		this.longitude = longitude;
	}
}
