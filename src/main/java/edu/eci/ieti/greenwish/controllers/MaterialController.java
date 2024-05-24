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

import edu.eci.ieti.greenwish.exceptions.MaterialNotFoundException;
import edu.eci.ieti.greenwish.models.Material;
import edu.eci.ieti.greenwish.models.dto.MaterialDto;
import edu.eci.ieti.greenwish.services.MaterialService;
import lombok.RequiredArgsConstructor;

/**
 * Controller class for managing materials.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/materials")
public class MaterialController {

    private final MaterialService materialService;

    /**
     * Get all materials.
     * 
     * @return A ResponseEntity containing a list of Material objects.
     */
    @GetMapping
    public ResponseEntity<List<Material>> all() {
        return ResponseEntity.ok(materialService.findAll());
    }

    /**
     * Get a material by its ID.
     * 
     * @param id The ID of the material.
     * @return A ResponseEntity containing the Material object if found, or a not
     *         found response if not found.
     */
    @GetMapping("{id}")
    public ResponseEntity<Material> findById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(materialService.findById(id));
        } catch (MaterialNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create a new material.
     * 
     * @param materialDto The MaterialDto object containing the material data.
     * @return A ResponseEntity containing the created Material object and the
     *         location URI.
     */
    @PostMapping
    public ResponseEntity<Material> create(@RequestBody MaterialDto materialDto) {
        Material savedMaterial = materialService.save(materialDto);
        URI location = URI.create(String.format("/materials/%s", savedMaterial.getId()));
        return ResponseEntity.created(location).body(savedMaterial);
    }

    /**
     * Update a material by its ID.
     * 
     * @param id          The ID of the material to be updated.
     * @param materialDto The MaterialDto object containing the updated material
     *                    data.
     * @return A ResponseEntity with the HTTP status indicating the success of the
     *         update operation.
     */
    @PutMapping("{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") String id, @RequestBody MaterialDto materialDto) {
        try {
            materialService.update(materialDto, id);
            return ResponseEntity.ok().build();
        } catch (MaterialNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a material by its ID.
     * 
     * @param id The ID of the material to be deleted.
     * @return A ResponseEntity with the HTTP status indicating the success of the
     *         delete operation.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id) {
        try {
            materialService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (MaterialNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
