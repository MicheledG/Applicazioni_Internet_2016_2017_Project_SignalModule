package it.polito.ai.signal.model;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/*
 * Class to fill in the request body to CREATE a signal
 */

public class CreatedSignal {
	
	private Coordinates coordinates;

	@NotEmpty
	private String address;
	
	@NotEmpty
	private String description;
	
	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
