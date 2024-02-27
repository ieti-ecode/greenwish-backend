package edu.eci.ieti.ecored.controller.material;

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

import edu.eci.ieti.ecored.repository.document.Material;
import edu.eci.ieti.ecored.service.material.MaterialService;

@RestController
@RequestMapping("/v1/materials")
public class MaterialController {
    
    private final MaterialService materialService;

    public MaterialController(@Autowired MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public ResponseEntity<?> all() {
        return new ResponseEntity<>(materialService.all(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        Optional<Material> material = materialService.findById(id);
        if (material.isPresent()) {
            return new ResponseEntity<>(material.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MaterialDto materialDto) {
        Material material = new Material(materialDto);   
        return new ResponseEntity<>(materialService.save(material), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody MaterialDto materialDto) {
        Optional<Material> material = materialService.findById(id);
        if (material.isPresent()) {
            materialService.update(materialDto, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMaterial(@PathVariable("id") String id) {
        if (materialService.findById(id).isPresent()) {
            materialService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
        }
    }
}
