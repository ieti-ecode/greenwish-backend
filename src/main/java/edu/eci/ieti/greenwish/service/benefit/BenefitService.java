package edu.eci.ieti.greenwish.service.benefit;

import java.util.List;

import edu.eci.ieti.greenwish.controller.benefit.BenefitDto;
import edu.eci.ieti.greenwish.exception.BenefitNotFoundException;
import edu.eci.ieti.greenwish.repository.document.Benefit;

public interface BenefitService {

    Benefit create(BenefitDto benefitDto);

    Benefit findById(String id) throws BenefitNotFoundException;

    List<Benefit> all();

    boolean deleteById(String id);

    Benefit update(BenefitDto benefitDto, String id);

}
