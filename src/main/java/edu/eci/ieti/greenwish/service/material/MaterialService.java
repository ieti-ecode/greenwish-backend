package edu.eci.ieti.greenwish.service.material;

import java.util.List;
import java.util.Optional;

import edu.eci.ieti.greenwish.controller.material.MaterialDto;
import edu.eci.ieti.greenwish.repository.document.Material;

public interface MaterialService {

    Material save(Material material);

    Optional<Material> findById(String id);

    List<Material> all();

    void deleteById(String id);

    void update(MaterialDto material, String id);

}
