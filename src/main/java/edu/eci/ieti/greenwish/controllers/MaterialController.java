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
import edu.eci.ieti.greenwish.models.domain.Material;
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
    @GetMapping("/{id}")
    public ResponseEntity<Material> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok(materialService.findById(id));
    }

    /**
     * Retrieves the image associated with the specified material ID.
     *
     * @param id The ID of the material.
     * @return The byte array representing the image.
     */
    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable("id") String id) {
        return materialService.getImage(id);
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
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedMaterial.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedMaterial);
    }

    /**
     * Uploads an image for a material with the specified ID.
     *
     * @param id    The ID of the material.
     * @param image The image file to be uploaded.
     * @return A ResponseEntity with a success status code if the image was uploaded
     *         successfully.
     * @throws ImageReadException If an error occurs while reading the image file.
     */
    @PostMapping("/{id}/image")
    public ResponseEntity<String> uploadImage(@PathVariable("id") String id, @RequestParam("image") MultipartFile image) {
        try {
            materialService.uploadImage(id, image);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            throw new ImageReadException(e.getMessage());
        }
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
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") String id, @RequestBody MaterialDto materialDto) {
        materialService.update(materialDto, id);
        return ResponseEntity.ok().build();
    }

    /**
     * Delete a material by its ID.
     * 
     * @param id The ID of the material to be deleted.
     * @return A ResponseEntity with the HTTP status indicating the success of the
     *         delete operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id) {
        materialService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
