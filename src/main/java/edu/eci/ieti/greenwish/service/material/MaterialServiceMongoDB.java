package edu.eci.ieti.greenwish.service.material;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.eci.ieti.greenwish.controller.material.MaterialDto;
import edu.eci.ieti.greenwish.exception.MaterialNotFoundException;
import edu.eci.ieti.greenwish.repository.MaterialRepository;
import edu.eci.ieti.greenwish.repository.document.Material;

@Service
public class MaterialServiceMongoDB implements MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialServiceMongoDB(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public Material save(Material material) {
        return materialRepository.save(material);
    }

    @Override
    public Optional<Material> findById(String id) {
        return materialRepository.findById(id);
    }

    @Override
    public List<Material> all() {
        return materialRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        materialRepository.deleteById(id);
    }

    @Override
    public void update(MaterialDto material, String id) {
        Optional<Material> optionalMaterial = materialRepository.findById(id);
        if (optionalMaterial.isPresent()) {
            Material materialToUpdate = optionalMaterial.get();
            materialToUpdate.setName(material.getName());
            materialToUpdate.setKiloValue(material.getKiloValue());
            materialToUpdate.setDescription(material.getDescription());
        } else {
            throw new MaterialNotFoundException();
        }
    }

}
