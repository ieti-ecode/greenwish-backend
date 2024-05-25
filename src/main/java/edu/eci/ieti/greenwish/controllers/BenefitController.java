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

import edu.eci.ieti.greenwish.exceptions.ImageReadException;
import edu.eci.ieti.greenwish.models.domain.Benefit;
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
    public ResponseEntity<List<Benefit>> all() {
        return ResponseEntity.ok(benefitService.findAll());
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
        return ResponseEntity.ok(benefitService.findById(id));
    }

    /**
     * Retrieves the image associated with the specified benefit ID.
     *
     * @param id The ID of the benefit.
     * @return The byte array representing the image.
     */
    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable("id") String id) {
        return benefitService.getImage(id);
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
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBenefit.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedBenefit);
    }

    /**
     * Uploads an image for a benefit with the specified ID.
     *
     * @param id    The ID of the benefit.
     * @param image The image file to be uploaded.
     * @return A ResponseEntity with a success status code if the image was uploaded
     *         successfully.
     * @throws ImageReadException If an error occurs while reading the image file.
     */
    @PostMapping("/{id}/image")
    public ResponseEntity<String> uploadImage(@PathVariable("id") String id, @RequestParam("image") MultipartFile image) {
        try {
            benefitService.uploadImage(id, image);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            throw new ImageReadException(e.getMessage());
        }
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
    public ResponseEntity<HttpStatus> update(@PathVariable("id") String id, @RequestBody BenefitDto benefitDto) {
        benefitService.update(benefitDto, id);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a benefit by its ID.
     * 
     * @param id the ID of the benefit to delete
     * @return the ResponseEntity indicating the success of the deletion, or a not
     *         found response if the benefit does not exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id) {
        benefitService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
