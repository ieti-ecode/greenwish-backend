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

@RestController
@RequestMapping("/v1/materials")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(@Autowired MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public ResponseEntity<List<Material>> all() {
        return ResponseEntity.ok(materialService.all());
    }

    @GetMapping("{id}")
    public ResponseEntity<Material> findById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(materialService.findById(id));
        } catch (MaterialNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Material> create(@RequestBody MaterialDto materialDto) {
        Material savedMaterial = materialService.save(materialDto);
        URI location = URI.create(String.format("/v1/materials/%s", savedMaterial.getId()));
        return ResponseEntity.created(location).body(savedMaterial);
    }

    @PutMapping("{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") String id, @RequestBody MaterialDto materialDto) {
        try {
            materialService.update(materialDto, id);
            return ResponseEntity.ok().build();
        } catch (MaterialNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

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
