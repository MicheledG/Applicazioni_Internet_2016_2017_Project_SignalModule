package it.polito.ai.signal.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rates")
public class Rate {

	@Id
	private String id;
	
	/* Which signal is rated */
	@Indexed
	private Coordinates coordinates;
	
	/* Who did the rate */
	private String username;
	
	/* The rate :) */
	private int rate;

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

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
}
