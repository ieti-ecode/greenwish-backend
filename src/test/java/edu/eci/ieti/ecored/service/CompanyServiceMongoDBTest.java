package edu.eci.ieti.ecored.service;


import edu.eci.ieti.ecored.Service.BenefitServiceMongoDB;
import edu.eci.ieti.ecored.Service.CompanyServiceMongoDB;
import edu.eci.ieti.ecored.controller.benefit.BenefitDto;
import edu.eci.ieti.ecored.controller.company.CompanyDto;
import edu.eci.ieti.ecored.exception.BenefitNotFoundException;
import edu.eci.ieti.ecored.exception.CompanyNotFoundException;
import edu.eci.ieti.ecored.repository.BenefitRepository;
import edu.eci.ieti.ecored.repository.CompanyRepository;
import edu.eci.ieti.ecored.repository.document.Benefit;
import edu.eci.ieti.ecored.repository.document.Company;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CompanyServiceMongoDBTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceMongoDB companyServiceMongoDB;


    @Test
    @Order(1)
    public void testFindAllCompanies(){
        List<Company> companiesListMock = Arrays.asList(
                new Company("Exito", "3568456", "Cll325"),
                new Company("GitHub", "654789", "CR5")
        );
        Mockito.when(companyRepository.findAll()).thenReturn(companiesListMock);
        List<Company> companies = companyServiceMongoDB.all();
        Assertions.assertNotNull(companies);
        Assertions.assertTrue(companies.size() > 0);
        Assertions.assertEquals("CR5", companies.get(1).getAddress());
    }

    @Test
    @Order(2)
    public void testFindCompanyById() throws CompanyNotFoundException {
        Optional<Company> companyMock = Optional.of(new Company("GitHub", "654789", "CR5"));
        Mockito.when(companyRepository.findById("1")).thenReturn(companyMock);
        Company company = companyServiceMongoDB.findById("1");
        Assertions.assertNotNull(company);
        Assertions.assertEquals("GitHub", company.getName());
    }

    @Test
    @Order(3)
    public void testCreateCompany() {
        CompanyDto companyFromController = new CompanyDto("GitHub", "654789", "CR5");
        Company companyMock = new Company(companyFromController.getName(), companyFromController.getPhoneNumber(),
                companyFromController.getAddress());
        Mockito.when(companyRepository.save(companyMock)).thenReturn(companyMock);
        Company companySaved = companyRepository.save(companyMock);
        Assertions.assertNotNull(companySaved);
        Assertions.assertEquals("GitHub", companySaved.getName());
    }
}
