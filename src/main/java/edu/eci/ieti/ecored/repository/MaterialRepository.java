package edu.eci.ieti.ecored.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.eci.ieti.ecored.repository.document.Material;

@Repository
public interface MaterialRepository extends MongoRepository<Material, String> {
}