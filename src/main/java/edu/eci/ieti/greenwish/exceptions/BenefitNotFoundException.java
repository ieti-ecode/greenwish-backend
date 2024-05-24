package edu.eci.ieti.greenwish.exceptions;

/**
 * Exception thrown when a benefit is not found.
 */
public class BenefitNotFoundException extends ResourceNotFoundException {

    /**
     * Constructs a new BenefitNotFoundException with a default error message.
     */
    public BenefitNotFoundException(Object fieldValue) {
        super("Benefit", "id", fieldValue);
    }
    
}
