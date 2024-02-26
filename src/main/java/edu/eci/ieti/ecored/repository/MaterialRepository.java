package edu.eci.ieti.ecored.repository;

import edu.eci.ieti.ecored.repository.document.Material;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MaterialRepository extends MongoRepository<Material, String> {
}