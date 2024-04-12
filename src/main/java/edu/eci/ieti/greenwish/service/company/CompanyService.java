package edu.eci.ieti.greenwish.service.company;

import edu.eci.ieti.greenwish.controller.company.CompanyDto;
import edu.eci.ieti.greenwish.exception.CompanyNotFoundException;
import edu.eci.ieti.greenwish.repository.document.Company;

import java.util.List;

public interface CompanyService {

    Company save(CompanyDto companyDto);

    Company findById(String id) throws CompanyNotFoundException;

    List<Company> all();

    void deleteById(String id) throws CompanyNotFoundException;

    void update(CompanyDto companyDto, String id) throws CompanyNotFoundException;

}
