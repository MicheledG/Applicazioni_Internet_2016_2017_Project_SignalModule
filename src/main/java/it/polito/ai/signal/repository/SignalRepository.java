package it.polito.ai.signal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.polito.ai.signal.model.Coordinates;
import it.polito.ai.signal.model.Signal;

@Repository
public interface SignalRepository extends MongoRepository<Signal, String> {
	public Signal findOneByCoordinates(Coordinates coordinates);

}
