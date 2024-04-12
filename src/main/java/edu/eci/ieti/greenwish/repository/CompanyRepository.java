package edu.eci.ieti.greenwish.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.eci.ieti.greenwish.repository.document.Company;

public interface CompanyRepository extends MongoRepository<Company, String> {
}
