package edu.eci.ieti.ecored.Service;

import edu.eci.ieti.ecored.controller.benefit.BenefitDto;
import edu.eci.ieti.ecored.exception.BenefitNotFoundException;
import edu.eci.ieti.ecored.repository.BenefitRepository;
import edu.eci.ieti.ecored.repository.document.Benefit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BenefitServiceMongoDB implements BenefitService{

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
        if (optionalBenefit.isPresent()){
            return optionalBenefit.get();
        }else {
            throw new BenefitNotFoundException();
        }
    }

    @Override
    public List<Benefit> all() {
        return benefitRepository.findAll();
    }

    @Override
    public boolean deleteById(String id) {
        if (benefitRepository.existsById(id)){
            benefitRepository.deleteById(id);
            return true;
        }
            return false;
    }

    @Override
    public Benefit update(BenefitDto benefitDto, String id) {
        if (benefitRepository.findById(id).isPresent()){
            Benefit benefit = benefitRepository.findById(id).get();
            benefit.setValue(benefit.getValue());
            benefit.setName(benefit.getName());
            benefit.setDescription(benefitDto.getDescription());
            return benefit;
        }
        return null;
    }
}
