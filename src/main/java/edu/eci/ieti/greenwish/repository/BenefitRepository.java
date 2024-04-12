package edu.eci.ieti.greenwish.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.eci.ieti.greenwish.repository.document.Benefit;

public interface BenefitRepository extends MongoRepository<Benefit, String> {
}