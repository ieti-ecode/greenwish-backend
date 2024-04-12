package edu.eci.ieti.greenwish.service.material;

import edu.eci.ieti.greenwish.controller.material.MaterialDto;
import edu.eci.ieti.greenwish.exception.MaterialNotFoundException;
import edu.eci.ieti.greenwish.repository.document.Material;

import java.util.List;

public interface MaterialService {

    Material save(MaterialDto materialDto);

    Material findById(String id) throws MaterialNotFoundException;

    List<Material> all();

    void deleteById(String id) throws MaterialNotFoundException;

    void update(MaterialDto material, String id) throws MaterialNotFoundException;

}
