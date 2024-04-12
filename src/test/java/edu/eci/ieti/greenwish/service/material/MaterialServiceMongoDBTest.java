package edu.eci.ieti.greenwish.service.material;

import edu.eci.ieti.greenwish.controller.material.MaterialDto;
import edu.eci.ieti.greenwish.exception.BenefitNotFoundException;
import edu.eci.ieti.greenwish.exception.MaterialNotFoundException;
import edu.eci.ieti.greenwish.repository.MaterialRepository;
import edu.eci.ieti.greenwish.repository.document.Material;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaterialServiceMongoDBTest {

    @Mock
    private MaterialRepository materialRepository;

    @InjectMocks
    private MaterialServiceMongoDB materialServiceMongoDB;

    @Test
    void testFindAllMaterials() {
        List<Material> materials = Arrays.asList(
                new Material("Material 1", "Description 1", 1),
                new Material("Material 2", "Description 2", 2));
        Mockito.when(materialRepository.findAll()).thenReturn(materials);
        List<Material> allMaterials = materialServiceMongoDB.all();
        Assertions.assertNotNull(allMaterials);
        assertEquals(2, allMaterials.size());
        assertEquals(materials, allMaterials);
        verify(materialRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdExistingMaterial() throws BenefitNotFoundException {
        String id = "1";
        MaterialDto materialDto = new MaterialDto("Material 1", "Description 1", 1);
        Material material = new Material(materialDto);
        when(materialRepository.findById(id)).thenReturn(Optional.of(material));
        Material foundMaterial = materialServiceMongoDB.findById(id);
        verify(materialRepository, times(1)).findById(id);
        assertEquals(material, foundMaterial);
    }

    @Test
    void testFindByIdNotFound() {
        String id = "1";
        when(materialRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(MaterialNotFoundException.class, () -> materialServiceMongoDB.findById(id));
        verify(materialRepository, times(1)).findById(id);
    }

    @Test
    void testSaveNewMaterial() {
        MaterialDto materialDto = new MaterialDto("Material 1", "Description 1", 1);
        Material material = new Material(materialDto);
        Mockito.when(materialRepository.save(material)).thenReturn(material);
        Material savedMaterial = materialServiceMongoDB.save(materialDto);
        Assertions.assertNotNull(savedMaterial);
        verify(materialRepository, times(1)).save(material);
        assertEquals(material, savedMaterial);
    }

    @Test
    void testUpdateExistingMaterial() {
        String id = "1";
        MaterialDto materialDto = new MaterialDto("Material 1", "Description 1", 1);
        Material material = new Material("Material 2", "Description 2", 2);
        when(materialRepository.findById(id)).thenReturn(Optional.of(material));
        materialServiceMongoDB.update(materialDto, id);
        verify(materialRepository, times(1)).findById(id);
        verify(materialRepository, times(1)).save(material);
        assertEquals(materialDto.getName(), material.getName());
        assertEquals(materialDto.getDescription(), material.getDescription());
        assertEquals(materialDto.getKiloValue(), material.getKiloValue());
    }

    @Test
    void testUpdateNotExistingMaterial() {
        String id = "1";
        MaterialDto materialDto = new MaterialDto("Material 1", "Description 1", 1);
        when(materialRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(MaterialNotFoundException.class, () -> materialServiceMongoDB.update(materialDto, id));
        verify(materialRepository, times(1)).findById(id);
    }


    @Test
    void testDeleteExistingMaterial() {
        String id = "1";
        when(materialRepository.existsById(id)).thenReturn(true);
        materialServiceMongoDB.deleteById(id);
        verify(materialRepository, times(1)).deleteById(id);
        verify(materialRepository, times(1)).existsById(id);
    }

    @Test
    void testDeleteNotExistingMaterial() {
        String id = "1";
        when(materialRepository.existsById(id)).thenReturn(false);
        assertThrows(MaterialNotFoundException.class, () -> materialServiceMongoDB.deleteById(id));
        verify(materialRepository, times(1)).existsById(id);
    }


}
