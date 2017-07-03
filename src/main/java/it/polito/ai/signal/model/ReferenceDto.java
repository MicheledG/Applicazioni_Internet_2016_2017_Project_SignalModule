package it.polito.ai.signal.model;

/**
 * A DTO for a new signal reference.
 */
public class ReferenceDto {
	
	private Coordinates coordinates;
	
	public ReferenceDto(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

}
