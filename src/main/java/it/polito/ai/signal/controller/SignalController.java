package it.polito.ai.signal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.polito.ai.signal.model.Coordinates;
import it.polito.ai.signal.model.SignalDto;
import it.polito.ai.signal.model.RatingDto;
import it.polito.ai.signal.model.ReferenceDto;
import it.polito.ai.signal.model.Signal;
import it.polito.ai.signal.service.SignalService;

@RestController
@CrossOrigin(origins="*", allowedHeaders={"Accept", "Authorization", "Content-Type"})
public class SignalController {
	
	@Autowired
	SignalService signalService;	
	
	/**
	 * Create a new signal.
	 * 
	 * @param signal
	 * @return
	 */
	@RequestMapping(value = "/signals", method = RequestMethod.POST)
	public ResponseEntity<String> create(@RequestBody @Validated SignalDto signalDto) {
		
		// Check if a signal with the same coordinates already exists
		if (!signalService.exists(signalDto.getCoordinates())) {
			
			// Get the username of the authenticated user from the SecurityContext
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String username = auth.getName();
			
			// Create a signal
			signalService.createSignal(signalDto, username);
			
			// Signal created => 201
			return new ResponseEntity<String>(HttpStatus.CREATED);
			
		} else {
			// Already exists => 409
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}		
	}
	
	/**
	 * Get all the signals.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/signals", method = RequestMethod.GET)
	public ResponseEntity<List<Signal>> getSignals() {
		
		// Get all the valid signals
		List<Signal> signals = signalService.readSignals();
		
		// There is no signal to return => 204
		if (signals.isEmpty() || signals == null) {
			return new ResponseEntity<List<Signal>>(HttpStatus.NO_CONTENT);
		}
		
		// There are signals to return => 200
		return new ResponseEntity<List<Signal>>(signals, HttpStatus.OK);
	}
	
	/**
	 * Add a new rating for an existing signal.
	 * 
	 * @param rating
	 * @return
	 */
	@RequestMapping(value = "/ratings", method = RequestMethod.POST)
	public ResponseEntity<String> addRating(@RequestBody @Validated RatingDto ratingDto) {

		// Get the username of the authenticated user from the SecurityContext
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		
		// Set the coordinates of the signal to rate
		Coordinates coordinates = new Coordinates(ratingDto.getLatitude(), ratingDto.getLongitude());
		
		// If the signal doesn't exist => 404
		if (!signalService.exists(coordinates)) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);			
		}
		
		// Add a rating to the specified signal
		if (signalService.createRating(coordinates, username, ratingDto.getRating())) {
			// Rating added => 201
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} else {
			// Adding rating failed => 400
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}		
	}
	
	/**
	 * Update the referenced signal by updating its timestamp of last referenced time
	 * @param signal
	 * @return
	 */
	@RequestMapping(value = "/signals/reference", method = RequestMethod.POST)
	public ResponseEntity<String> referenceSignal(@RequestBody @Validated ReferenceDto referenceDto) {

		// If there isn't a signal with that coordinates => 404
		if (!signalService.exists(referenceDto.getCoordinates())) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		
		// Update the referenced signal
		if (!signalService.updateSignal(referenceDto)) {
			// The signal doesn't exist anymore (no valid) => 404
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		
		// Signal updated => 200
		return new ResponseEntity<String>(HttpStatus.OK);
		
	}
	
	/*
	 * To rate a signal
	 */
//	@RequestMapping(value = "/signals/{lat}/{lng}/ratings", method = RequestMethod.POST)
//	public ResponseEntity<String> ratingSignal(@PathVariable("lat") String lat, @PathVariable("lng") String lng, @RequestBody @Validated RatingWrapper rating) {
//		double latitude;
//		double longitude;
//		try {
//			latitude = Double.parseDouble(lat);
//			longitude = Double.parseDouble(lng);
//		}
//		catch (Exception e) {
//			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
//		}		
//		
//		// Get the username of the authenticated user from the SecurityContext
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String username = auth.getName();
//		Coordinates coordinates = new Coordinates(latitude, longitude);
//		if (!signalService.exists(coordinates)) {
//			/* The signal you are trying to vote does not exist! */
//			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);			
//		}
//		if (signalService.addRating(coordinates, username, rating.getRating())) {
//			/* All good */
//			return new ResponseEntity<String>(HttpStatus.CREATED);
//		}
//		else {
//			/* Something went wrong - almost impossible */
//			return new ResponseEntity<String>(HttpStatus.SERVICE_UNAVAILABLE);
//		}		
//	}
	
//	/*
//	 * Someone referenced an already existing signal, just update LastReference date
//	 */
//	@RequestMapping(value = "/signals/{lat}/{lng}", method = RequestMethod.PATCH)
//	public ResponseEntity<String> referenceSignal(@PathVariable("lat") String lat, @PathVariable("lng") String lng) {
//		double latitude;
//		double longitude;
//		try {
//			latitude = Double.parseDouble(lat);
//			longitude = Double.parseDouble(lng);
//		}
//		catch (Exception e) {
//			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
//		}
//		Coordinates coordinates = new Coordinates(latitude, longitude);
//		if (!signalService.exists(coordinates)) {
//			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
//		}
//		ReferencedSignal signal = new ReferencedSignal(coordinates);
//		if (!signalService.updateSignal(signal)) {
//			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);	//JUST IN CASE
//		}
//		
//		return new ResponseEntity<String>(HttpStatus.OK);
//		
//	}
}
