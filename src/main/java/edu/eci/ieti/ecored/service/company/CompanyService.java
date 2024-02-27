package edu.eci.ieti.ecored.service.company;

import java.util.List;

import edu.eci.ieti.ecored.controller.company.CompanyDto;
import edu.eci.ieti.ecored.exception.CompanyNotFoundException;
import edu.eci.ieti.ecored.repository.document.Company;

public interface CompanyService {

    Company create(CompanyDto companyDto);

    Company findById(String id) throws CompanyNotFoundException;

    List<Company> all();

    boolean deleteById(String id);

    Company update(CompanyDto companyDto, String id);

}
