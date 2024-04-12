package edu.eci.ieti.greenwish.controller.benefit;

import edu.eci.ieti.greenwish.exception.BenefitNotFoundException;
import edu.eci.ieti.greenwish.repository.document.Benefit;
import edu.eci.ieti.greenwish.service.benefit.BenefitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class BenefitControllerTest {

    @Mock
    private BenefitService benefitService;

    @InjectMocks
    private BenefitController benefitController;

    private MockMvc mockMvc;

    private final String BASE_URL = "/v1/benefits/";

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(benefitController).build();
    }

    @Test
    void testFindAllBenefits() throws Exception {
        List<Benefit> benefits = new ArrayList<>();
        benefits.add(new Benefit("Carulla", 10000));
        benefits.add(new Benefit("Falabella", 20000));
        when(benefitService.all()).thenReturn(benefits);
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Carulla")))
                .andExpect(jsonPath("$[0].value", is(10000)))
                .andExpect(jsonPath("$[1].name", is("Falabella")))
                .andExpect(jsonPath("$[1].value", is(20000)));
        verify(benefitService, times(1)).all();
    }


    @Test
    void testFindByIdExistingBenefit() throws Exception {
        String id = "1";
        Benefit benefit = new Benefit("Carulla", 10000);
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
        mockMvc.perform(get(BASE_URL + id))
                .andExpect(status().isNotFound());
        verify(benefitService, times(1)).findById(id);
    }

    @Test
    void testSaveNewBenefit() throws Exception {
        BenefitDto benefitDto = new BenefitDto("Carulla", 10000);
        Benefit benefit = new Benefit(benefitDto);
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
