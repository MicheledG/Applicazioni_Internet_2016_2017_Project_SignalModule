package it.polito.ai.signal.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.polito.ai.signal.model.Coordinates;
import it.polito.ai.signal.model.Rating;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {
	
	public Rating findOneByCoordinatesAndUsername(Coordinates coordinates, String username);
	public List<Rating> findByCoordinates(Coordinates coordinates);
}
