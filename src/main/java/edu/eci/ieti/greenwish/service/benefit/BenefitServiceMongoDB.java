package edu.eci.ieti.greenwish.service.benefit;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.ieti.greenwish.controller.benefit.BenefitDto;
import edu.eci.ieti.greenwish.exception.BenefitNotFoundException;
import edu.eci.ieti.greenwish.repository.BenefitRepository;
import edu.eci.ieti.greenwish.repository.document.Benefit;

@Service
public class BenefitServiceMongoDB implements BenefitService {

    private final BenefitRepository benefitRepository;

    public BenefitServiceMongoDB(@Autowired BenefitRepository benefitRepository) {
        this.benefitRepository = benefitRepository;
    }

    @Override
    public Benefit create(BenefitDto benefitDto) {
        return benefitRepository.save(new Benefit(benefitDto.getName(), benefitDto.getValue()));
    }

    @Override
    public Benefit findById(String id) throws BenefitNotFoundException {
        Optional<Benefit> optionalBenefit = benefitRepository.findById(id);
        if (optionalBenefit.isPresent()) {
            return optionalBenefit.get();
        } else {
            throw new BenefitNotFoundException();
        }
    }

    @Override
    public List<Benefit> all() {
        return benefitRepository.findAll();
    }

    @Override
    public boolean deleteById(String id) {
        if (benefitRepository.existsById(id)) {
            benefitRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Benefit update(BenefitDto benefitDto, String id) {
        Optional<Benefit> optionalBenefit = benefitRepository.findById(id);
        if (optionalBenefit.isPresent()) {
            Benefit benefit = optionalBenefit.get();
            benefit.setValue(benefit.getValue());
            benefit.setName(benefit.getName());
            benefit.setDescription(benefitDto.getDescription());
            return benefit;
        }
        return null;
    }
}
