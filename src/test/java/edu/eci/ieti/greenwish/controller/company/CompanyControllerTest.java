package edu.eci.ieti.greenwish.controller.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.eci.ieti.greenwish.repository.document.Company;
import edu.eci.ieti.greenwish.service.company.CompanyService;

class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void all() {
        // Arrange
        Company company1 = new Company("Exito", "3568456", "Cll325");
        Company company2 = new Company("GitHub", "654789", "CR5");
        when(companyService.all()).thenReturn(Arrays.asList(company1, company2));
        // Act
        ResponseEntity<List<Company>> response = companyController.all();
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        assertEquals("CR5", response.getBody().get(1).getAddress());
    }

    @Test
    void findById() {
        // Arrange
        String companyId = "1";
        Company company2 = new Company("GitHub", "654789", "CR5");
        when(companyService.findById(companyId)).thenReturn(company2);
        // Act
        ResponseEntity<Company> response = companyController.findById(companyId);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("GitHub", Objects.requireNonNull(response.getBody()).getName());
    }

    @Test
    void create() {
        // Arrangeg
        CompanyDto companyDto = new CompanyDto("GitHub", "654789", "CR5");
        Company company = new Company(companyDto.getName(), companyDto.getPhoneNumber(), companyDto.getAddress());
        when(companyService.save(companyDto)).thenReturn(company);
        // Act
        ResponseEntity<Company> response = companyController.create(companyDto);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void update() {
        // Arrange
        String companyId = "1";
        CompanyDto companyDto = new CompanyDto("GitHub", "654789", "CR5");
        Company company = new Company(companyDto.getName(), companyDto.getPhoneNumber(), companyDto.getAddress());
        when(companyService.findById(companyId)).thenReturn(company);
        // Act
        ResponseEntity<HttpStatus> response = companyController.update(companyDto, companyId);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void delete() {
        // Arrange
        String companyId = "1";
        Company company = new Company("GitHub", "654789", "CR5");
        when(companyService.findById(companyId)).thenReturn(company);
        // Act
        ResponseEntity<HttpStatus> response = companyController.delete(companyId);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}