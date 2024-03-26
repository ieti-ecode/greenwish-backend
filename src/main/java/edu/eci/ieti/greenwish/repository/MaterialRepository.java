package edu.eci.ieti.greenwish.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.eci.ieti.greenwish.repository.document.Material;

@Repository
public interface MaterialRepository extends MongoRepository<Material, String> {
}