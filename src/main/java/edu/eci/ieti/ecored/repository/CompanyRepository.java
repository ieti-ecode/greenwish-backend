package edu.eci.ieti.ecored.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.eci.ieti.ecored.repository.document.Company;

public interface CompanyRepository extends MongoRepository<Company, String> {
}
