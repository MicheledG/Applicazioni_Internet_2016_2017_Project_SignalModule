package it.polito.ai.signal.model;

import javax.validation.constraints.NotNull;

/**
 * A DTO for a signal to create.
 */
public class SignalDto {
	
	private Coordinates coordinates;

	@NotNull
	private String address;
	
	@NotNull
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
