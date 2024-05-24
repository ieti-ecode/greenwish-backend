package edu.eci.ieti.greenwish.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.eci.ieti.greenwish.exceptions.CompanyNotFoundException;
import edu.eci.ieti.greenwish.exceptions.UserNotFoundException;
import edu.eci.ieti.greenwish.models.domain.Company;
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
                .build();
        return companyRepository.save(company);
    }

    @Override
    public void update(CompanyDto companyDto, String id) throws CompanyNotFoundException {
        Company companyToUpdate = findById(id);
        companyToUpdate.setDescription(companyDto.getDescription());
        companyToUpdate.setName(companyDto.getName());
        companyToUpdate.setAddress(companyDto.getAddress());
        companyToUpdate.setDescription(companyDto.getDescription());
        companyRepository.save(companyToUpdate);
    }

    public void uploadImage(String id, MultipartFile image) throws UserNotFoundException, IOException {
        Company company = findById(id);
        company.setImage(new Binary(BsonBinarySubType.BINARY, image.getBytes()));
        companyRepository.save(company);
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company findById(String id) throws CompanyNotFoundException {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty())
            throw new CompanyNotFoundException(id);
        return optionalCompany.get();
    }

    public byte[] getImage(String id) {
        Company company = findById(id);
        if (company.getImage() == null)
            throw new CompanyNotFoundException(id);
        return company.getImage().getData();
    }

    @Override
    public void deleteById(String id) throws CompanyNotFoundException {
        if (!companyRepository.existsById(id))
            throw new CompanyNotFoundException(id);
        companyRepository.deleteById(id);
    }

}
