package edu.eci.ieti.greenwish.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.eci.ieti.greenwish.exceptions.CompanyNotFoundException;
import edu.eci.ieti.greenwish.exceptions.ImageReadException;
import edu.eci.ieti.greenwish.models.domain.Company;
import edu.eci.ieti.greenwish.models.dto.CompanyDto;
import edu.eci.ieti.greenwish.services.CompanyService;
import lombok.RequiredArgsConstructor;

/**
 * This class represents the controller for managing company-related operations.
 * It handles HTTP requests and provides endpoints for retrieving, creating,
 * updating, and deleting companies.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    /**
     * Retrieves all companies.
     *
     * @return ResponseEntity with a list of Company objects.
     */
    @GetMapping
    public ResponseEntity<List<Company>> all() {
        return ResponseEntity.ok(companyService.findAll());
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
    public ResponseEntity<Company> findById(@PathVariable String id) {
        return ResponseEntity.ok(companyService.findById(id));
    }

    /**
     * Retrieves the image associated with the specified company ID.
     *
     * @param id The ID of the company.
     * @return The byte array representing the image.
     */
    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable("id") String id) {
        return companyService.getImage(id);
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
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCompany.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedCompany);
    }

    /**
     * Uploads an image for a company with the specified ID.
     *
     * @param id    The ID of the company.
     * @param image The image file to be uploaded.
     * @return A ResponseEntity with a success status code if the image was uploaded
     *         successfully.
     * @throws ImageReadException If an error occurs while reading the image file.
     */
    @PostMapping("/{id}/image")
    public ResponseEntity<String> uploadImage(@PathVariable("id") String id, @RequestParam("image") MultipartFile image) {
        try {
            companyService.uploadImage(id, image);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            throw new ImageReadException(e.getMessage());
        }
    }

    /**
     * Updates a company with the given companyDto and id.
     *
     * @param companyDto The updated company information.
     * @param id         The id of the company to be updated.
     * @return A ResponseEntity with the HTTP status of the update operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") String id, @RequestBody CompanyDto companyDto) {
        companyService.update(companyDto, id);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a company with the specified ID.
     *
     * @param id the ID of the company to delete
     * @return a ResponseEntity with the HTTP status code indicating the result of
     *         the deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id) {
        companyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
