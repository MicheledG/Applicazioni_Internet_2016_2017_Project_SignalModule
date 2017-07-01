package it.polito.ai.signal.model;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "signals")
public class Signal {
	
	@Id
	@JsonIgnore
	private String id;
	
	@Indexed
	private Coordinates coordinates;
	
	private String author;
	
	private String address;
	
	private String description;
	
	private Date origin;
	
	@JsonIgnore
	private Date lastAccess;
	
	private double average;		//average rating

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

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getOrigin() {
		return origin;
	}

	public void setOrigin(Date origin) {
		this.origin = origin;
	}

	public Date getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}
	
}
