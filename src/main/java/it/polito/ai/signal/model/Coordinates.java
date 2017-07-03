package it.polito.ai.signal.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class Coordinates {

	@NotNull
	@Range(min = -90, max = 90)
	private double latitude;

	@NotNull
	@Range(min = -180, max = 180)
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
