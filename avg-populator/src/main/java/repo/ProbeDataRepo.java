package repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import model.ProbeDataDoc;

public interface ProbeDataRepo extends MongoRepository<ProbeDataDoc, Object>{

}
