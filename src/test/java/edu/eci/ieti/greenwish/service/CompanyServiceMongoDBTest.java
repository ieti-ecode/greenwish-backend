package edu.eci.ieti.greenwish.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import edu.eci.ieti.greenwish.controller.company.CompanyDto;
import edu.eci.ieti.greenwish.exception.CompanyNotFoundException;
import edu.eci.ieti.greenwish.repository.CompanyRepository;
import edu.eci.ieti.greenwish.repository.document.Company;
import edu.eci.ieti.greenwish.service.company.CompanyServiceMongoDB;

@SpringBootTest
class CompanyServiceMongoDBTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceMongoDB companyServiceMongoDB;

    @Test
    @Order(1)
    void testFindAllCompanies() {
        List<Company> companiesListMock = Arrays.asList(
                new Company("Exito", "3568456", "Cll325"),
                new Company("GitHub", "654789", "CR5"));
        Mockito.when(companyRepository.findAll()).thenReturn(companiesListMock);
        List<Company> companies = companyServiceMongoDB.all();
        Assertions.assertNotNull(companies);
        Assertions.assertTrue(companies.size() > 0);
        Assertions.assertEquals("CR5", companies.get(1).getAddress());
    }

    @Test
    @Order(2)
    void testFindCompanyById() throws CompanyNotFoundException {
        Optional<Company> companyMock = Optional.of(new Company("GitHub", "654789", "CR5"));
        Mockito.when(companyRepository.findById("1")).thenReturn(companyMock);
        Company company = companyServiceMongoDB.findById("1");
        Assertions.assertNotNull(company);
        Assertions.assertEquals("GitHub", company.getName());
    }

    @Test
    @Order(3)
    void testCreateCompany() {
        CompanyDto companyFromController = new CompanyDto("GitHub", "654789", "CR5");
        Company companyMock = new Company(companyFromController.getName(), companyFromController.getPhoneNumber(),
                companyFromController.getAddress());
        Mockito.when(companyRepository.save(companyMock)).thenReturn(companyMock);
        Company companySaved = companyRepository.save(companyMock);
        Assertions.assertNotNull(companySaved);
        Assertions.assertEquals("GitHub", companySaved.getName());
    }

}
