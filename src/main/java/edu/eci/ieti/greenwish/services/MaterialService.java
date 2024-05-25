package edu.eci.ieti.greenwish.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.eci.ieti.greenwish.exceptions.MaterialNotFoundException;
import edu.eci.ieti.greenwish.exceptions.UserNotFoundException;
import edu.eci.ieti.greenwish.models.domain.Material;
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

    public void uploadImage(String id, MultipartFile image) throws UserNotFoundException, IOException {
        Material material = findById(id);
        material.setImage(new Binary(BsonBinarySubType.BINARY, image.getBytes()));
        materialRepository.save(material);
    }

    @Override
    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    @Override
    public Material findById(String id) throws MaterialNotFoundException {
        Optional<Material> optionalMaterial = materialRepository.findById(id);
        if (optionalMaterial.isEmpty())
            throw new MaterialNotFoundException(id);
        return optionalMaterial.get();
    }

    public byte[] getImage(String id) {
        Material material = findById(id);
        if (material.getImage() == null)
            throw new MaterialNotFoundException(id);
        return material.getImage().getData();
    }

    @Override
    public void deleteById(String id) throws MaterialNotFoundException {
        if (!materialRepository.existsById(id))
            throw new MaterialNotFoundException(id);
        materialRepository.deleteById(id);
    }

}
