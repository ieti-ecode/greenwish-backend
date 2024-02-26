package edu.eci.ieti.ecored.repository;

import edu.eci.ieti.ecored.repository.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}