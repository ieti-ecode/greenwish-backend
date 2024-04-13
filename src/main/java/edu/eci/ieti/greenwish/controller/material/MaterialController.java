package edu.eci.ieti.greenwish.controller.material;

import edu.eci.ieti.greenwish.exception.MaterialNotFoundException;
import edu.eci.ieti.greenwish.repository.document.Material;
import edu.eci.ieti.greenwish.service.material.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Controller class for managing materials.
 */
@RestController
@RequestMapping("/v1/materials")
public class MaterialController {

    private final MaterialService materialService;

    /**
     * Constructor for MaterialController.
     * 
     * @param materialService The MaterialService instance to be used.
     */
    public MaterialController(@Autowired MaterialService materialService) {
        this.materialService = materialService;
    }

    /**
     * Get all materials.
     * 
     * @return A ResponseEntity containing a list of Material objects.
     */
    @GetMapping
    public ResponseEntity<List<Material>> all() {
        return ResponseEntity.ok(materialService.all());
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
        URI location = URI.create(String.format("/v1/materials/%s", savedMaterial.getId()));
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
