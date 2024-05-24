package edu.eci.ieti.greenwish.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import edu.eci.ieti.greenwish.exceptions.MaterialNotFoundException;
import edu.eci.ieti.greenwish.models.Material;
import edu.eci.ieti.greenwish.models.dto.MaterialDto;
import edu.eci.ieti.greenwish.services.MaterialService;

@ExtendWith(MockitoExtension.class)
class MaterialControllerTest {

    @Mock
    private MaterialService materialService;

    @InjectMocks
    private MaterialController materialController;

    private MockMvc mockMvc;
    private List<Material> materials;
    private Material material;
    private final String BASE_URL = "/materials/";

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(materialController).build();
        material = new Material("1", "Material 1", "Description 1", 1);
        Material material2 = new Material("2", "Material 2", "Description 2", 2);
        materials = List.of(material, material2);
    }

    @Test
    void testFindAllMaterials() throws Exception {
        when(materialService.findAll()).thenReturn(materials);
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Material 1")))
                .andExpect(jsonPath("$[0].description", is("Description 1")))
                .andExpect(jsonPath("$[0].kiloValue", is(1)))
                .andExpect(jsonPath("$[1].name", is("Material 2")))
                .andExpect(jsonPath("$[1].description", is("Description 2")))
                .andExpect(jsonPath("$[1].kiloValue", is(2)));
        verify(materialService, times(1)).findAll();
    }

    @Test
    void testFindByIdExistingMaterial() throws Exception {
        String id = "1";
        when(materialService.findById(id)).thenReturn(material);
        mockMvc.perform(get(BASE_URL + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Material 1")))
                .andExpect(jsonPath("$.description", is("Description 1")))
                .andExpect(jsonPath("$.kiloValue", is(1)));
        verify(materialService, times(1)).findById(id);
    }

    @Test
    void testFindByIdNotExistingMaterial() throws Exception {
        String id = "511";
        when(materialService.findById(id)).thenThrow(new MaterialNotFoundException());
        mockMvc.perform(get(BASE_URL + id))
                .andExpect(status().isNotFound());
        verify(materialService, times(1)).findById(id);
    }

    @Test
    void testSaveNewMaterial() throws Exception {
        MaterialDto materialDto = new MaterialDto("Material 1", "Description 1", 1);
        when(materialService.save(any())).thenReturn(material);
        String json = "{\"name\":\"" + materialDto.getName() + "\",\"description\":\""
                + materialDto.getDescription()
                + "\",\"address\":" + materialDto.getKiloValue() + "}";
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
        verify(materialService, times(1)).save(any());
    }

    @Test
    void testUpdateExistingMaterial() throws Exception {
        String id = "1";
        MaterialDto materialDto = new MaterialDto("Material 1", "Description 1", 1);
        String json = "{\"name\":\"" + materialDto.getName() + "\",\"description\":\""
                + materialDto.getDescription()
                + "\",\"address\":" + materialDto.getKiloValue() + "}";
        mockMvc.perform(put(BASE_URL + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        verify(materialService, times(1)).update(any(), eq(id));
    }

    @Test
    void testUpdateNotExistingMaterial() throws Exception {
        String id = "1";
        MaterialDto materialDto = new MaterialDto("Material 1", "Description 1", 1);
        String json = "{\"name\":\"" + materialDto.getName() + "\",\"description\":\""
                + materialDto.getDescription()
                + "\",\"address\":" + materialDto.getKiloValue() + "}";
        doThrow(new MaterialNotFoundException()).when(materialService).update(any(), eq(id));
        mockMvc.perform(put(BASE_URL + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
        verify(materialService, times(1)).update(any(), eq(id));
    }

    @Test
    void testDeleteExistingCompany() throws Exception {
        String id = "1";
        mockMvc.perform(delete(BASE_URL + id))
                .andExpect(status().isNoContent());
        verify(materialService, times(1)).deleteById(id);
    }

    @Test
    void testDeleteNotExistingCompany() throws Exception {
        String id = "511";
        doThrow(new MaterialNotFoundException()).when(materialService).deleteById(id);
        mockMvc.perform(delete(BASE_URL + id))
                .andExpect(status().isNotFound());
        verify(materialService, times(1)).deleteById(id);
    }
}