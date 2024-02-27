package edu.eci.ieti.ecored.service.material;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.ieti.ecored.controller.material.MaterialDto;
import edu.eci.ieti.ecored.repository.MaterialRepository;
import edu.eci.ieti.ecored.repository.document.Material;

@Service
public class MaterialServiceMongoDB implements MaterialService {

    private final MaterialRepository materialRepository;

    @Autowired
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
        Material materialToUpdate = materialRepository.findById(id).get();
        materialToUpdate.setName(material.getName());
        materialToUpdate.setKiloValue(material.getKiloValue());
        materialToUpdate.setDescription(material.getDescription());
    }

}
