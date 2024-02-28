package repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import model.SensorRangeDoc;

public interface SensorRangeRepo extends MongoRepository<SensorRangeDoc, Long> {

}
