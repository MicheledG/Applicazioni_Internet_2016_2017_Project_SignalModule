package it.polito.ai.signal.model;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class Coordinates {

	@NotEmpty
	@Pattern(regexp = "^[-+]?([1-8]?[0-9](.[0-9]+)?|90(.0+)?)$")
	private double latitude;

	@NotEmpty
	@Pattern(regexp = "^[-+]?([1-8]?[0-9](.[0-9]+)?|90(.0+)?)$")
	private double longitude;

	public Coordinates() {
	}

	public Coordinates(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
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

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
