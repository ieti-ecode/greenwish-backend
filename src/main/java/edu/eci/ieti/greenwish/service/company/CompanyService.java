package edu.eci.ieti.greenwish.service.company;

import java.util.List;

import edu.eci.ieti.greenwish.controller.company.CompanyDto;
import edu.eci.ieti.greenwish.exception.CompanyNotFoundException;
import edu.eci.ieti.greenwish.repository.document.Company;

public interface CompanyService {

    Company create(CompanyDto companyDto);

    Company findById(String id) throws CompanyNotFoundException;

    List<Company> all();

    boolean deleteById(String id);

    Company update(CompanyDto companyDto, String id);

}
