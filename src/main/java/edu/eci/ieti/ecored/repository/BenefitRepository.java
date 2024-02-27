package edu.eci.ieti.ecored.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.eci.ieti.ecored.repository.document.Benefit;

public interface BenefitRepository extends MongoRepository<Benefit, String> {
}