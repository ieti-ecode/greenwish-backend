package edu.eci.ieti.greenwish.service.company;

import edu.eci.ieti.greenwish.controller.company.CompanyDto;
import edu.eci.ieti.greenwish.exception.CompanyNotFoundException;
import edu.eci.ieti.greenwish.repository.CompanyRepository;
import edu.eci.ieti.greenwish.repository.document.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the CompanyService interface that uses MongoDB as the
 * underlying data store.
 */
@Service
public class CompanyServiceMongoDB implements CompanyService {

    private final CompanyRepository companyRepository;

    /**
     * Constructs a new instance of the CompanyServiceMongoDB class.
     *
     * @param companyRepository the repository used for accessing company data
     */
    public CompanyServiceMongoDB(@Autowired CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company save(CompanyDto companyDto) {
        return companyRepository.save(new Company(companyDto));
    }

    @Override
    public Company findById(String id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty())
            throw new CompanyNotFoundException();
        return optionalCompany.get();
    }

    @Override
    public List<Company> all() {
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
