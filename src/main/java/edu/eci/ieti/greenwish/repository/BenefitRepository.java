package edu.eci.ieti.greenwish.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.eci.ieti.greenwish.repository.document.Benefit;

/**
 * This interface represents a repository for managing Benefit objects in the
 * database.
 * It extends the MongoRepository interface, providing CRUD operations for
 * Benefit entities.
 */
@Repository
public interface BenefitRepository extends MongoRepository<Benefit, String> {
}