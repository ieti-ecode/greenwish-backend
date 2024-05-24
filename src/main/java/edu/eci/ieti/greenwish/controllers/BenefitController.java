package edu.eci.ieti.greenwish.controllers;

import java.net.URI;
import java.util.List;

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

import edu.eci.ieti.greenwish.exceptions.BenefitNotFoundException;
import edu.eci.ieti.greenwish.models.Benefit;
import edu.eci.ieti.greenwish.models.dto.BenefitDto;
import edu.eci.ieti.greenwish.services.BenefitService;
import lombok.RequiredArgsConstructor;

/**
 * This class represents the controller for managing benefits in the system.
 * It handles HTTP requests related to benefits and interacts with the
 * BenefitService.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/benefits")
public class BenefitController {

    private final BenefitService benefitService;

    /**
     * Retrieves all benefits.
     * 
     * @return a list of all benefits
     */
    @GetMapping
    public List<Benefit> all() {
        return benefitService.findAll();
    }

    /**
     * Retrieves a benefit by its ID.
     * 
     * @param id the ID of the benefit to retrieve
     * @return the ResponseEntity containing the retrieved benefit, or a not found
     *         response if the benefit does not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<Benefit> findById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(benefitService.findById(id));
        } catch (BenefitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new benefit.
     * 
     * @param benefitDto the DTO containing the data for the new benefit
     * @return the ResponseEntity containing the created benefit and its location,
     *         or an error response if the creation fails
     */
    @PostMapping
    public ResponseEntity<Benefit> create(@RequestBody BenefitDto benefitDto) {
        Benefit savedBenefit = benefitService.save(benefitDto);
        URI location = URI.create(String.format("/benefits/%s", savedBenefit.getId()));
        return ResponseEntity.created(location).body(savedBenefit);
    }

    /**
     * Updates an existing benefit.
     * 
     * @param benefitDto the DTO containing the updated data for the benefit
     * @param id         the ID of the benefit to update
     * @return the ResponseEntity indicating the success of the update, or a not
     *         found response if the benefit does not exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody BenefitDto benefitDto, @PathVariable String id) {
        try {
            benefitService.update(benefitDto, id);
            return ResponseEntity.ok().build();
        } catch (BenefitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a benefit by its ID.
     * 
     * @param id the ID of the benefit to delete
     * @return the ResponseEntity indicating the success of the deletion, or a not
     *         found response if the benefit does not exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        try {
            benefitService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (BenefitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
