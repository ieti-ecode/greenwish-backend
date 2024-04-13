package edu.eci.ieti.greenwish.service.benefit;

import edu.eci.ieti.greenwish.controller.benefit.BenefitDto;
import edu.eci.ieti.greenwish.exception.BenefitNotFoundException;
import edu.eci.ieti.greenwish.repository.BenefitRepository;
import edu.eci.ieti.greenwish.repository.document.Benefit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the BenefitService interface that uses MongoDB as the data
 * source.
 */
@Service
public class BenefitServiceMongoDB implements BenefitService {

    private final BenefitRepository benefitRepository;

    /**
     * Constructs a new instance of the BenefitServiceMongoDB class.
     *
     * @param benefitRepository the repository for accessing benefit data in the
     *                          MongoDB database
     */
    public BenefitServiceMongoDB(@Autowired BenefitRepository benefitRepository) {
        this.benefitRepository = benefitRepository;
    }

    @Override
    public Benefit save(BenefitDto benefitDto) {
        return benefitRepository.save(new Benefit(benefitDto));
    }

    @Override
    public Benefit findById(String id) {
        Optional<Benefit> optionalBenefit = benefitRepository.findById(id);
        if (optionalBenefit.isEmpty())
            throw new BenefitNotFoundException();
        return optionalBenefit.get();
    }

    @Override
    public List<Benefit> all() {
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
