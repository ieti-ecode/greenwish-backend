package edu.eci.ieti.ecored.repository;

import edu.eci.ieti.ecored.repository.document.Material;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends MongoRepository<Material, String> {
}