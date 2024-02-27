package edu.eci.ieti.ecored.service.benefit;

import java.util.List;

import edu.eci.ieti.ecored.controller.benefit.BenefitDto;
import edu.eci.ieti.ecored.exception.BenefitNotFoundException;
import edu.eci.ieti.ecored.repository.document.Benefit;

public interface BenefitService {

    Benefit create(BenefitDto benefitDto);

    Benefit findById(String id) throws BenefitNotFoundException;

    List<Benefit> all();

    boolean deleteById(String id);

    Benefit update(BenefitDto benefitDto, String id);

}
