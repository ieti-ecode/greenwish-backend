package edu.eci.ieti.greenwish.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.eci.ieti.greenwish.models.Material;

/**
 * This interface represents a repository for managing Material objects in the
 * database.
 * It extends the MongoRepository interface, providing CRUD operations for
 * Material entities.
 */
@Repository
public interface MaterialRepository extends MongoRepository<Material, String> {
}
