package edu.eci.ieti.greenwish.controller.company;

import java.net.URI;
import java.util.List;

import edu.eci.ieti.greenwish.exception.MaterialNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.ieti.greenwish.exception.CompanyNotFoundException;
import edu.eci.ieti.greenwish.repository.document.Company;
import edu.eci.ieti.greenwish.service.company.CompanyService;

/**
 * This class represents the controller for managing company-related operations.
 * It handles HTTP requests and provides endpoints for retrieving, creating,
 * updating, and deleting companies.
 */
@RestController
@RequestMapping("/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    /**
     * Constructs a new CompanyController instance.
     *
     * @param companyService the CompanyService instance to be used for handling
     *                       company-related operations
     */
    public CompanyController(@Autowired CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Retrieves all companies.
     *
     * @return ResponseEntity with a list of Company objects.
     */
    @GetMapping
    public ResponseEntity<List<Company>> all() {
        return ResponseEntity.ok(companyService.all());
    }

    /**
     * Retrieves a company by its ID.
     *
     * @param id the ID of the company to retrieve
     * @return the ResponseEntity with the retrieved company, or a not found
     *         response if the company does not exist
     * @throws CompanyNotFoundException if the company with the given ID does not
     *                                  exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<Company> findById(@PathVariable String id) throws CompanyNotFoundException {
        try {
            return ResponseEntity.ok(companyService.findById(id));
        } catch (CompanyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new company.
     *
     * @param companyDto the company data transfer object containing the company
     *                   information
     * @return the ResponseEntity with the created company and the HTTP status code
     *         201 (Created)
     */
    @PostMapping
    public ResponseEntity<Company> create(@RequestBody CompanyDto companyDto) {
        Company savedCompany = companyService.save(companyDto);
        URI location = URI.create(String.format("/v1/companies/%s", savedCompany.getId()));
        return ResponseEntity.created(location).body(savedCompany);
    }

    /**
     * Updates a company with the given companyDto and id.
     *
     * @param companyDto The updated company information.
     * @param id         The id of the company to be updated.
     * @return A ResponseEntity with the HTTP status of the update operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody CompanyDto companyDto, @PathVariable String id) {
        try {
            companyService.update(companyDto, id);
            return ResponseEntity.ok().build();
        } catch (CompanyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a company with the specified ID.
     *
     * @param id the ID of the company to delete
     * @return a ResponseEntity with the HTTP status code indicating the result of
     *         the deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        try {
            companyService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (CompanyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
