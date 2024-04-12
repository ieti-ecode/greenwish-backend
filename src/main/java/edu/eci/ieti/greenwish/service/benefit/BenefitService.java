package edu.eci.ieti.greenwish.service.benefit;

import edu.eci.ieti.greenwish.controller.benefit.BenefitDto;
import edu.eci.ieti.greenwish.exception.BenefitNotFoundException;
import edu.eci.ieti.greenwish.repository.document.Benefit;

import java.util.List;

public interface BenefitService {

    Benefit save(BenefitDto benefitDto);

    Benefit findById(String id) throws BenefitNotFoundException;

    List<Benefit> all();

    void deleteById(String id) throws BenefitNotFoundException;

    void update(BenefitDto benefitDto, String id) throws BenefitNotFoundException;

}
