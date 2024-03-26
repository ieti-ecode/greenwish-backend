package edu.eci.ieti.greenwish.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.eci.ieti.greenwish.controller.company.CompanyController;
import edu.eci.ieti.greenwish.exception.CompanyNotFoundException;
import edu.eci.ieti.greenwish.repository.document.Company;
import edu.eci.ieti.greenwish.service.company.CompanyService;

@ExtendWith(MockitoExtension.class)
public class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    @Test
    void getBenefitById_BenefitExists() throws CompanyNotFoundException {
        String userId = "123";
        Company company = new Company("GitHub", "654789", "CR5");
        when(companyService.findById(userId)).thenReturn(company);

        ResponseEntity<Company> response = companyController.findById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("GitHub", response.getBody().getName());
    }

}
