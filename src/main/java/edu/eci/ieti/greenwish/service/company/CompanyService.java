package edu.eci.ieti.greenwish.service.company;

import edu.eci.ieti.greenwish.controller.company.CompanyDto;
import edu.eci.ieti.greenwish.exception.CompanyNotFoundException;
import edu.eci.ieti.greenwish.repository.document.Company;

import java.util.List;

/**
 * The CompanyService interface provides methods to manage companies.
 */
public interface CompanyService {

    /**
     * Saves a new company.
     *
     * @param companyDto the company data transfer object
     * @return the saved company
     */
    Company save(CompanyDto companyDto);

    /**
     * Finds a company by its ID.
     *
     * @param id the ID of the company to find
     * @return the found company
     * @throws CompanyNotFoundException if the company is not found
     */
    Company findById(String id) throws CompanyNotFoundException;

    /**
     * Retrieves all companies.
     *
     * @return a list of all companies
     */
    List<Company> all();

    /**
     * Deletes a company by its ID.
     *
     * @param id the ID of the company to delete
     * @throws CompanyNotFoundException if the company is not found
     */
    void deleteById(String id) throws CompanyNotFoundException;

    /**
     * Updates a company by its ID.
     *
     * @param companyDto the updated company data transfer object
     * @param id the ID of the company to update
     * @throws CompanyNotFoundException if the company is not found
     */
    void update(CompanyDto companyDto, String id) throws CompanyNotFoundException;

}
