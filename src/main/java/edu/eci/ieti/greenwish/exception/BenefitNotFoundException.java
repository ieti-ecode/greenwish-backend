package edu.eci.ieti.greenwish.exception;

/**
 * Exception thrown when a benefit is not found.
 */
public class BenefitNotFoundException extends RuntimeException {

    /**
     * Constructs a new BenefitNotFoundException with a default error message.
     */
    public BenefitNotFoundException() {
        super("Benefit not found");
    }

}
