package edu.eci.ieti.ecored.Service;

import edu.eci.ieti.ecored.controller.company.CompanyDto;
import edu.eci.ieti.ecored.exception.BenefitNotFoundException;
import edu.eci.ieti.ecored.exception.CompanyNotFoundException;
import edu.eci.ieti.ecored.repository.CompanyRepository;
import edu.eci.ieti.ecored.repository.document.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceMongoDB implements CompanyService{

    private final CompanyRepository companyRepository;

    public CompanyServiceMongoDB (@Autowired CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company create(CompanyDto companyDto) {
        return companyRepository.save(new Company(companyDto.getName(), companyDto.getPhoneNumber(), companyDto.getAddress()));
    }

    @Override
    public Company findById(String id) throws CompanyNotFoundException {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()){
            return optionalCompany.get();
        }else {
            throw new CompanyNotFoundException();
        }
    }

    @Override
    public List<Company> all() {
        return companyRepository.findAll();
    }

    @Override
    public boolean deleteById(String id) {
        if (companyRepository.existsById(id)){
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Company update(CompanyDto companyDto, String id) {
        if (companyRepository.findById(id).isPresent()) {
            Company company = companyRepository.findById(id).get();
            company.setDescription(companyDto.getDescription());
            company.setName(companyDto.getName());
            company.setAddress(companyDto.getAddress());
            company.setOppeningHours(companyDto.getOppeningHours());
            company.setDescription(companyDto.getDescription());

            return company;
        }
        return null;
    }

}
