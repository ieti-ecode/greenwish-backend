package edu.eci.ieti.ecored.Service;

import edu.eci.ieti.ecored.controller.benefit.BenefitDto;
import edu.eci.ieti.ecored.exception.BenefitNotFoundException;
import edu.eci.ieti.ecored.repository.document.Benefit;

import java.util.List;

public interface BenefitService {

    Benefit create(BenefitDto benefitDto);

    Benefit findById ( String id) throws BenefitNotFoundException;

    List<Benefit> all();

    boolean deleteById( String id );

    Benefit update(BenefitDto benefitDto, String id);
}
