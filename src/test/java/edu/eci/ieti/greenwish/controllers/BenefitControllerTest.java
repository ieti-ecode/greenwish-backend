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

import edu.eci.ieti.greenwish.exceptions.BenefitNotFoundException;
import edu.eci.ieti.greenwish.models.Benefit;
import edu.eci.ieti.greenwish.models.dto.BenefitDto;
import edu.eci.ieti.greenwish.services.BenefitService;

@ExtendWith(MockitoExtension.class)
class BenefitControllerTest {

    @Mock
    private BenefitService benefitService;

    @InjectMocks
    private BenefitController benefitController;

    private MockMvc mockMvc;
    private List<Benefit> benefits;
    private Benefit benefit;
    private final String BASE_URL = "/benefits/";

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(benefitController).build();
        benefit = new Benefit("1", "Carulla", "Bono", 10000);
        Benefit benefit2 = new Benefit("2", "Falabella", "Bono", 20000);
        benefits = List.of(benefit, benefit2);
    }

    @Test
    void testFindAllBenefits() throws Exception {
        when(benefitService.findAll()).thenReturn(benefits);
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Carulla")))
                .andExpect(jsonPath("$[0].value", is(10000)))
                .andExpect(jsonPath("$[1].name", is("Falabella")))
                .andExpect(jsonPath("$[1].value", is(20000)));
        verify(benefitService, times(1)).findAll();
    }

    @Test
    void testFindByIdExistingBenefit() throws Exception {
        String id = "1";
        when(benefitService.findById(id)).thenReturn(benefit);
        mockMvc.perform(get(BASE_URL + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Carulla")))
                .andExpect(jsonPath("$.value", is(10000)));
        verify(benefitService, times(1)).findById(id);
    }

    @Test
    void testFindByIdNotExistingBenefit() throws Exception {
        String id = "511";
        when(benefitService.findById(id)).thenThrow(new BenefitNotFoundException());
        mockMvc.perform(get(BASE_URL + id)).andExpect(status().isNotFound());
        verify(benefitService, times(1)).findById(id);
    }

    @Test
    void testSaveNewBenefit() throws Exception {
        BenefitDto benefitDto = new BenefitDto("Carulla", 10000);
        when(benefitService.save(any())).thenReturn(benefit);
        String json = "{\"name\":\"" + benefitDto.getName() + "\",\"value\":" + benefitDto.getValue() + "}";
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
        verify(benefitService, times(1)).save(any());
    }

    @Test
    void testUpdateExistingBenefit() throws Exception {
        BenefitDto benefitDto = new BenefitDto("Carulla", 10000);
        String id = "1";
        String json = "{\"name\":\"" + benefitDto.getName() + "\",\"value\":" + benefitDto.getValue() + "}";
        mockMvc.perform(put(BASE_URL + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        verify(benefitService, times(1)).update(any(), eq(id));
    }

    @Test
    void testUpdateNotExistingBenefit() throws Exception {
        BenefitDto benefitDto = new BenefitDto("Carulla", 10000);
        String id = "511";
        String json = "{\"name\":\"" + benefitDto.getName() + "\",\"value\":" + benefitDto.getValue() + "}";
        doThrow(new BenefitNotFoundException()).when(benefitService).update(any(), eq(id));
        mockMvc.perform(put(BASE_URL + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
        verify(benefitService, times(1)).update(any(), eq(id));
    }

    @Test
    void testDeleteExistingBenefit() throws Exception {
        String id = "1";
        mockMvc.perform(delete(BASE_URL + id))
                .andExpect(status().isNoContent());
        verify(benefitService, times(1)).deleteById(id);
    }

    @Test
    void testDeleteNotExistingBenefit() throws Exception {
        String id = "511";
        doThrow(new BenefitNotFoundException()).when(benefitService).deleteById(id);
        mockMvc.perform(delete(BASE_URL + id))
                .andExpect(status().isNotFound());
        verify(benefitService, times(1)).deleteById(id);
    }

}
