package edu.eci.ieti.ecored.service.material;

import java.util.List;
import java.util.Optional;

import edu.eci.ieti.ecored.controller.material.MaterialDto;
import edu.eci.ieti.ecored.repository.document.Material;

public interface MaterialService {

    Material save(Material material);

    Optional<Material> findById(String id);

    List<Material> all();

    void deleteById(String id);

    void update(MaterialDto material, String id);
    
}
