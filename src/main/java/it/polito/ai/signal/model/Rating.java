package it.polito.ai.signal.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ratings")
public class Rating {

	@Id
	private String id;
	
	/* Which signal is rated */
	@Indexed
	private Coordinates coordinates;
	
	/* Who is rating */
	private String username;
	
	/* The rating :) */
	private int rating;

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getRating() {
		return rating;
	}

	public void setRate(int rating) {
		this.rating = rating;
	}
}
