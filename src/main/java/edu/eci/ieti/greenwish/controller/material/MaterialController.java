package edu.eci.ieti.greenwish.controller.material;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import edu.eci.ieti.greenwish.repository.document.Material;
import edu.eci.ieti.greenwish.service.material.MaterialService;

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
        Optional<Material> material = materialService.findById(id);
        if (material.isPresent()) {
            return ResponseEntity.ok(material.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Material> create(@RequestBody MaterialDto materialDto) {
        Material material = materialService.save(new Material(materialDto));
        URI location = URI.create(String.format("/v1/materials/%s", material.getId()));
        return ResponseEntity.created(location).body(material);
    }

    @PutMapping("{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") String id, @RequestBody MaterialDto materialDto) {
        Optional<Material> material = materialService.findById(id);
        if (material.isPresent()) {
            materialService.update(materialDto, id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteMaterial(@PathVariable("id") String id) {
        if (materialService.findById(id).isPresent()) {
            materialService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
