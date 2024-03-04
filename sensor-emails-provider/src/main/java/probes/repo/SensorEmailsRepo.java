package probes.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import probes.model.SensorEmailsDoc;
public interface SensorEmailsRepo extends MongoRepository<SensorEmailsDoc, Long> {

}
