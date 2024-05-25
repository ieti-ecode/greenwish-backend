package edu.eci.ieti.greenwish.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.eci.ieti.greenwish.exceptions.BenefitNotFoundException;
import edu.eci.ieti.greenwish.exceptions.UserNotFoundException;
import edu.eci.ieti.greenwish.models.domain.Benefit;
import edu.eci.ieti.greenwish.models.dto.BenefitDto;
import edu.eci.ieti.greenwish.repositories.BenefitRepository;
import lombok.RequiredArgsConstructor;

/**
 * Implementation of the BenefitService interface that uses MongoDB as the data
 * source.
 */
@Service
@RequiredArgsConstructor
public class BenefitService implements CrudService<Benefit, BenefitDto, String, BenefitNotFoundException> {

    private final BenefitRepository benefitRepository;

    @Override
    public Benefit save(BenefitDto benefitDto) {
        Benefit benefit = Benefit.builder()
                .name(benefitDto.getName())
                .description(benefitDto.getDescription())
                .value(benefitDto.getValue())
                .build();
        return benefitRepository.save(benefit);
    }

    @Override
    public void update(BenefitDto benefitDto, String id) throws BenefitNotFoundException {
        Benefit benefitToUpdate = findById(id);
        benefitToUpdate.setValue(benefitDto.getValue());
        benefitToUpdate.setName(benefitDto.getName());
        benefitToUpdate.setDescription(benefitDto.getDescription());
        benefitRepository.save(benefitToUpdate);
    }

    public void uploadImage(String id, MultipartFile image) throws UserNotFoundException, IOException {
        Benefit benefit = findById(id);
        benefit.setImage(new Binary(BsonBinarySubType.BINARY, image.getBytes()));
        benefitRepository.save(benefit);
    }

    @Override
    public List<Benefit> findAll() {
        return benefitRepository.findAll();
    }

    @Override
    public Benefit findById(String id) throws BenefitNotFoundException {
        Optional<Benefit> optionalBenefit = benefitRepository.findById(id);
        if (optionalBenefit.isEmpty())
            throw new BenefitNotFoundException(id);
        return optionalBenefit.get();
    }

    public byte[] getImage(String id) {
        Benefit benefit = findById(id);
        if (benefit.getImage() == null)
            throw new BenefitNotFoundException(id);
        return benefit.getImage().getData();
    }

    @Override
    public void deleteById(String id) throws BenefitNotFoundException {
        if (!benefitRepository.existsById(id))
            throw new BenefitNotFoundException(id);
        benefitRepository.deleteById(id);
    }

}
