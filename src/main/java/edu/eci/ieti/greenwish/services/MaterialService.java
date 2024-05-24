package edu.eci.ieti.greenwish.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.eci.ieti.greenwish.exceptions.MaterialNotFoundException;
import edu.eci.ieti.greenwish.models.Material;
import edu.eci.ieti.greenwish.models.dto.MaterialDto;
import edu.eci.ieti.greenwish.repositories.MaterialRepository;
import lombok.RequiredArgsConstructor;

/**
 * This class implements the MaterialService interface using MongoDB as the data
 * source.
 * It provides methods to save, find, retrieve all, delete by ID, and update
 * materials.
 */
@Service
@RequiredArgsConstructor
public class MaterialService implements CrudService<Material, MaterialDto, String, MaterialNotFoundException> {

    private final MaterialRepository materialRepository;

    @Override
    public Material save(MaterialDto materialDto) {
        Material material = Material.builder()
                .name(materialDto.getName())
                .kiloValue(materialDto.getKiloValue())
                .description(materialDto.getDescription())
                .build();
        return materialRepository.save(material);
    }

    @Override
    public void update(MaterialDto materialDto, String id) throws MaterialNotFoundException {
        Material materialToUpdate = findById(id);
        materialToUpdate.setName(materialDto.getName());
        materialToUpdate.setKiloValue(materialDto.getKiloValue());
        materialToUpdate.setDescription(materialDto.getDescription());
        materialRepository.save(materialToUpdate);
    }

    @Override
    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    @Override
    public Material findById(String id) throws MaterialNotFoundException {
        Optional<Material> optionalMaterial = materialRepository.findById(id);
        if (optionalMaterial.isEmpty())
            throw new MaterialNotFoundException();
        return optionalMaterial.get();
    }

    @Override
    public void deleteById(String id) throws MaterialNotFoundException {
        if (!materialRepository.existsById(id))
            throw new MaterialNotFoundException();
        materialRepository.deleteById(id);
    }

}
