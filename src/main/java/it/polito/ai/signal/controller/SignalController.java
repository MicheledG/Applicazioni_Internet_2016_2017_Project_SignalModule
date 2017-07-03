package it.polito.ai.signal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.polito.ai.signal.model.Coordinates;
import it.polito.ai.signal.model.CreatedSignal;
import it.polito.ai.signal.model.RatingWrapper;
import it.polito.ai.signal.model.ReferencedSignal;
import it.polito.ai.signal.model.Signal;
import it.polito.ai.signal.service.SignalService;

@RestController
@CrossOrigin(origins="*", allowedHeaders={"Accept", "Authorization", "Content-Type"})
public class SignalController {
	
	@Autowired
	SignalService signalService;	
	
	/*
	 * Create a new signal - only if not existing yet
	 */
	@RequestMapping(value = "/signals", method = RequestMethod.POST)
	public ResponseEntity<String> create(@RequestBody @Validated CreatedSignal signal) {
		if (!signalService.exists(signal.getCoordinates())) {
			// Get the username of the authenticated user from the SecurityContext
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String username = auth.getName();
			
			//creating the signal - the create() method will resolve the username to get his nickname
			signalService.create(signal, username);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}		
	}
	
	/*
	 * To get the list of signals for printing in the client's map
	 */
	
	@RequestMapping(value = "/signals", method = RequestMethod.GET)
	public ResponseEntity<List<Signal>> getSignals() {
		List<Signal> signals = signalService.getAll();
		if (signals.isEmpty() || signals == null) {
			return new ResponseEntity<List<Signal>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Signal>>(signals, HttpStatus.OK);
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
	
	@RequestMapping(value = "/signals/{lat}/{lng}/ratings", method = RequestMethod.POST)
	public ResponseEntity<String> ratingSignal(@RequestBody @Validated RatingWrapper rating) {
		
		System.err.println(rating.getRating());
		// Get the username of the authenticated user from the SecurityContext
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Coordinates coordinates = new Coordinates(Double.parseDouble(rating.getLat()), Double.parseDouble(rating.getLng()));
		if (!signalService.exists(coordinates)) {
			/* The signal you are trying to vote does not exist! */
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);			
		}
		if (signalService.addRating(coordinates, username, rating.getRating())) {
			/* All good */
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}
		else {
			/* Something went wrong - almost impossible */
			return new ResponseEntity<String>(HttpStatus.SERVICE_UNAVAILABLE);
		}		
	}
	
	@RequestMapping(value = "/ratings", method = RequestMethod.POST)
	public ResponseEntity<String> addRating(@RequestBody @Validated RatingWrapper rating) {
		
		System.err.println();
		// Get the username of the authenticated user from the SecurityContext
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Coordinates coordinates = new Coordinates(Double.parseDouble(rating.getLat()), Double.parseDouble(rating.getLng()));
		if (!signalService.exists(coordinates)) {
			/* The signal you are trying to vote does not exist! */
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);			
		}
		if (signalService.addRating(coordinates, username, rating.getRating())) {
			/* All good */
			return new ResponseEntity<String>(HttpStatus.CREATED);
		}
		else {
			/* Something went wrong - almost impossible */
			return new ResponseEntity<String>(HttpStatus.SERVICE_UNAVAILABLE);
		}		
	}
	
	/*
	 * Someone referenced an already existing signal, just update LastReference date
	 */
	@RequestMapping(value = "/signals/{lat}/{lng}", method = RequestMethod.PATCH)
	public ResponseEntity<String> referenceSignal(@PathVariable("lat") String lat, @PathVariable("lng") String lng) {
		double latitude;
		double longitude;
		try {
			latitude = Double.parseDouble(lat);
			longitude = Double.parseDouble(lng);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		Coordinates coordinates = new Coordinates(latitude, longitude);
		if (!signalService.exists(coordinates)) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		ReferencedSignal signal = new ReferencedSignal(coordinates);
		if (!signalService.updateSignal(signal)) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);	//JUST IN CASE
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
		
	}
}
