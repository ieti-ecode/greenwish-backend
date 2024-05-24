package edu.eci.ieti.greenwish.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.eci.ieti.greenwish.exceptions.BenefitNotFoundException;
import edu.eci.ieti.greenwish.models.Benefit;
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
    public Benefit findById(String id) {
        Optional<Benefit> optionalBenefit = benefitRepository.findById(id);
        if (optionalBenefit.isEmpty())
            throw new BenefitNotFoundException();
        return optionalBenefit.get();
    }

    @Override
    public List<Benefit> findAll() {
        return benefitRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        if (!benefitRepository.existsById(id))
            throw new BenefitNotFoundException();
        benefitRepository.deleteById(id);
    }

    @Override
    public void update(BenefitDto benefitDto, String id) {
        Benefit benefitToUpdate = findById(id);
        benefitToUpdate.setValue(benefitDto.getValue());
        benefitToUpdate.setName(benefitDto.getName());
        benefitToUpdate.setDescription(benefitDto.getDescription());
        benefitRepository.save(benefitToUpdate);
    }

}
