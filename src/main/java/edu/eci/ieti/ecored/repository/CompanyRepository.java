package edu.eci.ieti.ecored.repository;

import edu.eci.ieti.ecored.repository.document.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<Company, String> {
}
