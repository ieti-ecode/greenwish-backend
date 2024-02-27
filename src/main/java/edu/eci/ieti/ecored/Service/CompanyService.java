package edu.eci.ieti.ecored.Service;

import edu.eci.ieti.ecored.controller.benefit.BenefitDto;
import edu.eci.ieti.ecored.controller.company.CompanyDto;
import edu.eci.ieti.ecored.exception.BenefitNotFoundException;
import edu.eci.ieti.ecored.exception.CompanyNotFoundException;
import edu.eci.ieti.ecored.repository.document.Benefit;
import edu.eci.ieti.ecored.repository.document.Company;

import java.util.List;

public interface CompanyService {

    Company create(CompanyDto companyDto);

    Company findById ( String id) throws CompanyNotFoundException;

    List<Company> all();

    boolean deleteById( String id );

    Company update(CompanyDto companyDto, String id);

}
