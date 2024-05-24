package edu.eci.ieti.greenwish.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.eci.ieti.greenwish.models.User;

/**
 * This interface represents a repository for managing User entities in the
 * database.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user to find.
     * @return An Optional containing the user with the specified email address, or
     *         an empty Optional if not found.
     */
    Optional<User> findByEmail(String email);

}
