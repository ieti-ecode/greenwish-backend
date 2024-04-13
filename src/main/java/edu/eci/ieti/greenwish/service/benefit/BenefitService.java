package edu.eci.ieti.greenwish.service.benefit;

import edu.eci.ieti.greenwish.controller.benefit.BenefitDto;
import edu.eci.ieti.greenwish.exception.BenefitNotFoundException;
import edu.eci.ieti.greenwish.repository.document.Benefit;

import java.util.List;

/**
 * The BenefitService interface provides methods to manage benefits.
 */
public interface BenefitService {

    /**
     * Saves a new benefit.
     *
     * @param benefitDto the benefit data transfer object
     * @return the saved benefit
     */
    Benefit save(BenefitDto benefitDto);

    /**
     * Finds a benefit by its ID.
     *
     * @param id the ID of the benefit to find
     * @return the found benefit
     * @throws BenefitNotFoundException if the benefit is not found
     */
    Benefit findById(String id) throws BenefitNotFoundException;

    /**
     * Retrieves all benefits.
     *
     * @return a list of all benefits
     */
    List<Benefit> all();

    /**
     * Deletes a benefit by its ID.
     *
     * @param id the ID of the benefit to delete
     * @throws BenefitNotFoundException if the benefit is not found
     */
    void deleteById(String id) throws BenefitNotFoundException;

    /**
     * Updates a benefit by its ID.
     *
     * @param benefitDto the updated benefit data transfer object
     * @param id the ID of the benefit to update
     * @throws BenefitNotFoundException if the benefit is not found
     */
    void update(BenefitDto benefitDto, String id) throws BenefitNotFoundException;

}
