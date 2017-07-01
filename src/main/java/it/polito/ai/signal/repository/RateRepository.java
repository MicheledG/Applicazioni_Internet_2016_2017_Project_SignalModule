package it.polito.ai.signal.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.polito.ai.signal.model.Coordinates;
import it.polito.ai.signal.model.Rate;

@Repository
public interface RateRepository extends MongoRepository<Rate, String> {
	
	public Rate findOneByCoordinatesAndUsername(Coordinates coordinates, String username);
	public List<Rate> findByCoordinates(Coordinates coordinates);
}
