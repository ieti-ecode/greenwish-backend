package edu.eci.ieti.greenwish.service.company;

import edu.eci.ieti.greenwish.controller.company.CompanyDto;
import edu.eci.ieti.greenwish.exception.CompanyNotFoundException;
import edu.eci.ieti.greenwish.repository.CompanyRepository;
import edu.eci.ieti.greenwish.repository.document.Company;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceMongoDBTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceMongoDB companyServiceMongoDB;

    @Test
    void testFindAllCompanies() {
        List<Company> companies = Arrays.asList(
                new Company("Exito", "3568456", "Cll325"),
                new Company("GitHub", "654789", "CR5"));
        Mockito.when(companyRepository.findAll()).thenReturn(companies);
        List<Company> allCompanies = companyServiceMongoDB.all();
        Assertions.assertNotNull(allCompanies);
        assertEquals(2, allCompanies.size());
        assertEquals(companies, allCompanies);
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdExistingCompany() throws CompanyNotFoundException {
        String id = "1";
        CompanyDto companyDto = new CompanyDto("GitHub", "654789", "CR5");
        Company company = new Company(companyDto);
        Mockito.when(companyRepository.findById(id)).thenReturn(Optional.of(company));
        Company foundCompany = companyServiceMongoDB.findById(id);
        verify(companyRepository, times(1)).findById(id);
        assertEquals(company, foundCompany);
    }

    @Test
    void testFindByIdNotFound() {
        String id = "1";
        when(companyRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(CompanyNotFoundException.class, () -> companyServiceMongoDB.findById(id));
        verify(companyRepository, times(1)).findById(id);
    }

    @Test
    void testSaveNewCompany() {
        CompanyDto companyDto = new CompanyDto("GitHub", "654789", "CR5");
        Company company = new Company(companyDto);
        Mockito.when(companyRepository.save(company)).thenReturn(company);
        Company savesCompany = companyServiceMongoDB.save(companyDto);
        verify(companyRepository, times(1)).save(company);
        assertEquals(company, savesCompany);
    }

    @Test
    void testUpdateExistingBenefit() {
        String id = "1";
        CompanyDto companyDto = new CompanyDto("GitHub", "654789", "CR5");
        Company company = new Company(companyDto);
        when(companyRepository.findById(id)).thenReturn(Optional.of(company));
        companyServiceMongoDB.update(companyDto, id);
        verify(companyRepository, times(1)).findById(id);
        verify(companyRepository, times(1)).save(company);
        assertEquals(companyDto.getName(), company.getName());
        assertEquals(companyDto.getPhoneNumber(), company.getPhoneNumber());
        assertEquals(companyDto.getAddress(), company.getAddress());
    }

    @Test
    void testUpdateNotExistingBenefit() {
        String id = "1";
        CompanyDto companyDto = new CompanyDto("GitHub", "654789", "CR5");
        when(companyRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(CompanyNotFoundException.class, () -> companyServiceMongoDB.update(companyDto, id));
        verify(companyRepository, times(1)).findById(id);
    }


    @Test
    void testDeleteExistingBenefit() {
        String id = "1";
        when(companyRepository.existsById(id)).thenReturn(true);
        companyServiceMongoDB.deleteById(id);
        verify(companyRepository, times(1)).deleteById(id);
        verify(companyRepository, times(1)).existsById(id);
    }

    @Test
    void testDeleteNotExistingBenefit() {
        String id = "1";
        when(companyRepository.existsById(id)).thenReturn(false);
        assertThrows(CompanyNotFoundException.class, () -> companyServiceMongoDB.deleteById(id));
        verify(companyRepository, times(1)).existsById(id);
    }

}
