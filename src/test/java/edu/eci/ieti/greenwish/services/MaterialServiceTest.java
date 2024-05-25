package edu.eci.ieti.greenwish.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.eci.ieti.greenwish.exceptions.BenefitNotFoundException;
import edu.eci.ieti.greenwish.exceptions.MaterialNotFoundException;
import edu.eci.ieti.greenwish.models.domain.Material;
import edu.eci.ieti.greenwish.models.dto.MaterialDto;
import edu.eci.ieti.greenwish.repositories.MaterialRepository;

@ExtendWith(MockitoExtension.class)
class MaterialServiceTest {

    @Mock
    private MaterialRepository materialRepository;

    @InjectMocks
    private MaterialService materialService;

    private List<Material> materials;
    private Material material;

    @BeforeEach
    void setUp() {
        material = new Material(null, "Material 1", "Description 1", 1, null);
        Material material2 = new Material("2", "Material 2", "Description 2", 2, null);
        materials = List.of(material, material2);
    }

    @Test
    void testFindAllMaterials() {
        when(materialRepository.findAll()).thenReturn(materials);
        List<Material> allMaterials = materialService.findAll();
        assertNotNull(allMaterials);
        assertEquals(2, allMaterials.size());
        assertEquals(materials, allMaterials);
        verify(materialRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdExistingMaterial() throws BenefitNotFoundException {
        String id = "1";
        when(materialRepository.findById(id)).thenReturn(Optional.of(material));
        Material foundMaterial = materialService.findById(id);
        verify(materialRepository, times(1)).findById(id);
        assertEquals(material, foundMaterial);
    }

    @Test
    void testFindByIdNotFound() {
        String id = "1";
        when(materialRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(MaterialNotFoundException.class, () -> materialService.findById(id));
        verify(materialRepository, times(1)).findById(id);
    }

    @Test
    void testSaveNewMaterial() {
        MaterialDto materialDto = new MaterialDto("Material 1", "Description 1", 1);
        when(materialRepository.save(material)).thenReturn(material);
        Material savedMaterial = materialService.save(materialDto);
        assertNotNull(savedMaterial);
        verify(materialRepository, times(1)).save(material);
        assertEquals(material, savedMaterial);
    }

    @Test
    void testUpdateExistingMaterial() {
        String id = "1";
        MaterialDto materialDto = new MaterialDto("Material 1", "Description 1", 1);
        when(materialRepository.findById(id)).thenReturn(Optional.of(material));
        materialService.update(materialDto, id);
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
        assertThrows(MaterialNotFoundException.class, () -> materialService.update(materialDto, id));
        verify(materialRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteExistingMaterial() {
        String id = "1";
        when(materialRepository.existsById(id)).thenReturn(true);
        materialService.deleteById(id);
        verify(materialRepository, times(1)).deleteById(id);
        verify(materialRepository, times(1)).existsById(id);
    }

    @Test
    void testDeleteNotExistingMaterial() {
        String id = "1";
        when(materialRepository.existsById(id)).thenReturn(false);
        assertThrows(MaterialNotFoundException.class, () -> materialService.deleteById(id));
        verify(materialRepository, times(1)).existsById(id);
    }

}
