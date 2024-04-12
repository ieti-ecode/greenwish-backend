package edu.eci.ieti.greenwish.controller.company;

import edu.eci.ieti.greenwish.exception.CompanyNotFoundException;
import edu.eci.ieti.greenwish.repository.document.Company;
import edu.eci.ieti.greenwish.service.company.CompanyService;
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
class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    private MockMvc mockMvc;

    private final String BASE_URL = "/v1/companies/";

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(companyController).build();
    }


    @Test
    void testFindAllBenefits() throws Exception {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("Exito", "3568456", "Cll325"));
        companies.add(new Company("GitHub", "654789", "CR5"));
        when(companyService.all()).thenReturn(companies);
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Exito")))
                .andExpect(jsonPath("$[0].phoneNumber", is("3568456")))
                .andExpect(jsonPath("$[0].address", is("Cll325")))
                .andExpect(jsonPath("$[1].name", is("GitHub")))
                .andExpect(jsonPath("$[1].phoneNumber", is("654789")))
                .andExpect(jsonPath("$[1].address", is("CR5")));
        verify(companyService, times(1)).all();
    }

    @Test
    void testFindByIdExistingCompany() throws Exception {
        String id = "1";
        Company company = new Company("GitHub", "654789", "CR5");
        when(companyService.findById(id)).thenReturn(company);
        mockMvc.perform(get(BASE_URL + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("GitHub")))
                .andExpect(jsonPath("$.phoneNumber", is("654789")))
                .andExpect(jsonPath("$.address", is("CR5")));
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
        Company company = new Company(companyDto);
        when(companyService.save(any())).thenReturn(company);
        String json = "{\"name\":\"" + companyDto.getName() + "\",\"phoneNumber\":\"" + companyDto.getPhoneNumber() + "\",\"address\":\"" + companyDto.getAddress() + "\"}";
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
        String json = "{\"name\":\"" + companyDto.getName() + "\",\"phoneNumber\":\"" + companyDto.getPhoneNumber() + "\",\"address\":\"" + companyDto.getAddress() + "\"}";
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
        String json = "{\"name\":\"" + companyDto.getName() + "\",\"phoneNumber\":\"" + companyDto.getPhoneNumber() + "\",\"address\":\"" + companyDto.getAddress() + "\"}";
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