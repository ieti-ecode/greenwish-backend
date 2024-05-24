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

import edu.eci.ieti.greenwish.exceptions.CompanyNotFoundException;
import edu.eci.ieti.greenwish.models.Company;
import edu.eci.ieti.greenwish.models.dto.CompanyDto;
import edu.eci.ieti.greenwish.services.CompanyService;

@ExtendWith(MockitoExtension.class)
class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    private MockMvc mockMvc;
    private List<Company> companies;
    private Company company;
    private final String BASE_URL = "/companies/";

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(companyController).build();
        company = new Company("1", "Exito", "Empresa", "3568456", "Cll325", "8");
        Company company2 = new Company("2", "GitHub", "Empresa", "654789", "CR5", "8");
        companies = List.of(company, company2);
    }

    @Test
    void testFindAllBenefits() throws Exception {
        when(companyService.findAll()).thenReturn(companies);
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Exito")))
                .andExpect(jsonPath("$[0].phoneNumber", is("3568456")))
                .andExpect(jsonPath("$[0].address", is("Cll325")))
                .andExpect(jsonPath("$[1].name", is("GitHub")))
                .andExpect(jsonPath("$[1].phoneNumber", is("654789")))
                .andExpect(jsonPath("$[1].address", is("CR5")));
        verify(companyService, times(1)).findAll();
    }

    @Test
    void testFindByIdExistingCompany() throws Exception {
        String id = "1";
        when(companyService.findById(id)).thenReturn(company);
        mockMvc.perform(get(BASE_URL + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Exito")))
                .andExpect(jsonPath("$.phoneNumber", is("3568456")))
                .andExpect(jsonPath("$.address", is("Cll325")));
        verify(companyService, times(1)).findById(id);
    }

    @Test
    void testFindByIdNotExistingBenefit() throws Exception {
        String id = "511";
        when(companyService.findById(id)).thenThrow(new CompanyNotFoundException());
        mockMvc.perform(get(BASE_URL + id))
                .andExpect(status().isNotFound());
        verify(companyService, times(1)).findById(id);
    }

    @Test
    void testSaveNewCompany() throws Exception {
        CompanyDto companyDto = new CompanyDto("GitHub", "654789", "CR5");
        when(companyService.save(any())).thenReturn(company);
        String json = "{\"name\":\"" + companyDto.getName() + "\",\"phoneNumber\":\""
                + companyDto.getPhoneNumber()
                + "\",\"address\":\"" + companyDto.getAddress() + "\"}";
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
        verify(companyService, times(1)).save(any());
    }

    @Test
    void testUpdateExistingCompany() throws Exception {
        String id = "1";
        CompanyDto companyDto = new CompanyDto("GitHub", "654789", "CR5");
        String json = "{\"name\":\"" + companyDto.getName() + "\",\"phoneNumber\":\""
                + companyDto.getPhoneNumber()
                + "\",\"address\":\"" + companyDto.getAddress() + "\"}";
        mockMvc.perform(put(BASE_URL + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        verify(companyService, times(1)).update(any(), eq(id));
    }

    @Test
    void testUpdateNotExistingCompany() throws Exception {
        String id = "511";
        CompanyDto companyDto = new CompanyDto("GitHub", "654789", "CR5");
        String json = "{\"name\":\"" + companyDto.getName() + "\",\"phoneNumber\":\""
                + companyDto.getPhoneNumber()
                + "\",\"address\":\"" + companyDto.getAddress() + "\"}";
        doThrow(new CompanyNotFoundException()).when(companyService).update(any(), eq(id));
        mockMvc.perform(put(BASE_URL + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
        verify(companyService, times(1)).update(any(), eq(id));
    }

    @Test
    void testDeleteExistingCompany() throws Exception {
        String id = "1";
        mockMvc.perform(delete(BASE_URL + id))
                .andExpect(status().isNoContent());
        verify(companyService, times(1)).deleteById(id);
    }

    @Test
    void testDeleteNotExistingCompany() throws Exception {
        String id = "511";
        doThrow(new CompanyNotFoundException()).when(companyService).deleteById(id);
        mockMvc.perform(delete(BASE_URL + id))
                .andExpect(status().isNotFound());
        verify(companyService, times(1)).deleteById(id);
    }
}