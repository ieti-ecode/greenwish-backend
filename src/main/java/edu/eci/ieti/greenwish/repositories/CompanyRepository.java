package edu.eci.ieti.greenwish.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.eci.ieti.greenwish.models.domain.Company;

/**
 * This interface represents a repository for managing Company entities in the
 * database.
 * It extends the MongoRepository interface, providing CRUD operations for
 * Company objects.
 */
@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {
}
