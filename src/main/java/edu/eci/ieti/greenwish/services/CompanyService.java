package edu.eci.ieti.greenwish.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.eci.ieti.greenwish.exceptions.CompanyNotFoundException;
import edu.eci.ieti.greenwish.models.Company;
import edu.eci.ieti.greenwish.models.dto.CompanyDto;
import edu.eci.ieti.greenwish.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;

/**
 * Implementation of the CompanyService interface that uses MongoDB as the
 * underlying data store.
 */
@Service
@RequiredArgsConstructor
public class CompanyService implements CrudService<Company, CompanyDto, String, CompanyNotFoundException> {

    private final CompanyRepository companyRepository;

    @Override
    public Company save(CompanyDto companyDto) {
        Company company = Company.builder()
                .name(companyDto.getName())
                .description(companyDto.getDescription())
                .address(companyDto.getAddress())
                .oppeningHours(companyDto.getOppeningHours())
                .build();
        return companyRepository.save(company);
    }

    @Override
    public Company findById(String id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty())
            throw new CompanyNotFoundException();
        return optionalCompany.get();
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        if (!companyRepository.existsById(id))
            throw new CompanyNotFoundException();
        companyRepository.deleteById(id);
    }

    @Override
    public void update(CompanyDto companyDto, String id) {
        Company companyToUpdate = findById(id);
        companyToUpdate.setDescription(companyDto.getDescription());
        companyToUpdate.setName(companyDto.getName());
        companyToUpdate.setAddress(companyDto.getAddress());
        companyToUpdate.setOppeningHours(companyDto.getOppeningHours());
        companyToUpdate.setDescription(companyDto.getDescription());
        companyRepository.save(companyToUpdate);
    }

}
