package edu.eci.ieti.greenwish.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.eci.ieti.greenwish.exceptions.CompanyNotFoundException;
import edu.eci.ieti.greenwish.models.domain.Company;
import edu.eci.ieti.greenwish.models.dto.CompanyDto;
import edu.eci.ieti.greenwish.repositories.CompanyRepository;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    private List<Company> companies;
    private Company company;

    @BeforeEach
    void setup() {
        company = new Company(null, "Exito", null, null, "Cll325", null);
        Company company2 = new Company("2", "GitHub", "Empresa", "654789", "CR5", null);
        companies = List.of(company, company2);
    }

    @Test
    void testFindAllCompanies() {
        when(companyRepository.findAll()).thenReturn(companies);
        List<Company> allCompanies = companyService.findAll();
        assertNotNull(allCompanies);
        assertEquals(2, allCompanies.size());
        assertEquals(companies, allCompanies);
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdExistingCompany() throws CompanyNotFoundException {
        String id = "1";
        when(companyRepository.findById(id)).thenReturn(Optional.of(company));
        Company foundCompany = companyService.findById(id);
        verify(companyRepository, times(1)).findById(id);
        assertEquals(company, foundCompany);
    }

    @Test
    void testFindByIdNotFound() {
        String id = "1";
        when(companyRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(CompanyNotFoundException.class, () -> companyService.findById(id));
        verify(companyRepository, times(1)).findById(id);
    }

    @Test
    void testSaveNewCompany() {
        CompanyDto companyDto = new CompanyDto("Exito", "3568456", "Cll325");
        when(companyRepository.save(company)).thenReturn(company);
        Company savesCompany = companyService.save(companyDto);
        verify(companyRepository, times(1)).save(company);
        assertEquals(company, savesCompany);
    }

    @Test
    void testUpdateExistingBenefit() {
        String id = "1";
        CompanyDto companyDto = new CompanyDto("Exito", "3568456", "Cll325");
        Company company = new Company(null, "Exito", null, "3568456", "Cll325", null);
        when(companyRepository.findById(id)).thenReturn(Optional.of(company));
        companyService.update(companyDto, id);
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
        assertThrows(CompanyNotFoundException.class, () -> companyService.update(companyDto, id));
        verify(companyRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteExistingBenefit() {
        String id = "1";
        when(companyRepository.existsById(id)).thenReturn(true);
        companyService.deleteById(id);
        verify(companyRepository, times(1)).deleteById(id);
        verify(companyRepository, times(1)).existsById(id);
    }

    @Test
    void testDeleteNotExistingBenefit() {
        String id = "1";
        when(companyRepository.existsById(id)).thenReturn(false);
        assertThrows(CompanyNotFoundException.class, () -> companyService.deleteById(id));
        verify(companyRepository, times(1)).existsById(id);
    }

}
