package edu.eci.ieti.ecored.repository;

import edu.eci.ieti.ecored.repository.document.Benefit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BenefitRepository extends MongoRepository<Benefit, String> {
}