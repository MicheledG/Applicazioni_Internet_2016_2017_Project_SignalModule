package it.polito.ai.signal.model;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * A DTO for a signal to create.
 */
public class SignalDto {
	
	@NotEmpty
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
