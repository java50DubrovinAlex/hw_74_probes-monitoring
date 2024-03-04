package probes.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import probes.model.SensorRangeDoc;

public interface SensorRangeRepo extends MongoRepository<SensorRangeDoc, Long> {

}
