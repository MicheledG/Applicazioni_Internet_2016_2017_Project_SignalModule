package it.polito.ai.signal.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * A DTO for rating data
 */
public class RatingDto {
	
	@Min(1)
	@Max(5)
	@NotEmpty
	private int rating;
	
	@NotEmpty
	@Pattern(regexp = "^[-+]?([1-8]?[0-9](.[0-9]+)?|90(.0+)?)$")
	private double latitude;
	
	@NotEmpty
	@Pattern(regexp = "^[-+]?(180(.0+)?|((1[0-7][0-9])|([1-9]?[0-9]))(.[0-9]+)?)$")
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
