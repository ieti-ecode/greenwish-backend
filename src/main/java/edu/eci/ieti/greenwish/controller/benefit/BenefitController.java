package edu.eci.ieti.greenwish.controller.benefit;

import edu.eci.ieti.greenwish.exception.BenefitNotFoundException;
import edu.eci.ieti.greenwish.exception.MaterialNotFoundException;
import edu.eci.ieti.greenwish.repository.document.Benefit;
import edu.eci.ieti.greenwish.service.benefit.BenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * This class represents the controller for managing benefits in the system.
 * It handles HTTP requests related to benefits and interacts with the BenefitService.
 */
@RestController
@RequestMapping("/v1/benefits")
public class BenefitController {

    private final BenefitService benefitService;

    /**
     * Constructs a new BenefitController with the specified BenefitService.
     * 
     * @param benefitService the BenefitService to be used by the controller
     */
    public BenefitController(@Autowired BenefitService benefitService) {
        this.benefitService = benefitService;
    }

    /**
     * Retrieves all benefits.
     * 
     * @return a list of all benefits
     */
    @GetMapping
    public List<Benefit> all() {
        return benefitService.all();
    }

    /**
     * Retrieves a benefit by its ID.
     * 
     * @param id the ID of the benefit to retrieve
     * @return the ResponseEntity containing the retrieved benefit, or a not found response if the benefit does not exist
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
     * @return the ResponseEntity containing the created benefit and its location, or an error response if the creation fails
     */
    @PostMapping
    public ResponseEntity<Benefit> create(@RequestBody BenefitDto benefitDto) {
        Benefit savedBenefit = benefitService.save(benefitDto);
        URI location = URI.create(String.format("/v1/benefits/%s", savedBenefit.getId()));
        return ResponseEntity.created(location).body(savedBenefit);
    }

    /**
     * Updates an existing benefit.
     * 
     * @param benefitDto the DTO containing the updated data for the benefit
     * @param id the ID of the benefit to update
     * @return the ResponseEntity indicating the success of the update, or a not found response if the benefit does not exist
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
     * @return the ResponseEntity indicating the success of the deletion, or a not found response if the benefit does not exist
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
