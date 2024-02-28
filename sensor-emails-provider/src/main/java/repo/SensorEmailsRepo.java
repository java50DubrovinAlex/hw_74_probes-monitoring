package repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import model.SensorEmailsDoc;
public interface SensorEmailsRepo extends MongoRepository<SensorEmailsDoc, Long> {

}
