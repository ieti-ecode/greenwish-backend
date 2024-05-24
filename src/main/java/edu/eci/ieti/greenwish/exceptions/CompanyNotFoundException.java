package edu.eci.ieti.greenwish.exceptions;

/**
 * Exception thrown when a company is not found.
 */
public class CompanyNotFoundException extends RuntimeException {

    /**
     * Constructs a new CompanyNotFoundException with a default error message.
     */
    public CompanyNotFoundException() {
        super("Company not found");
    }

}
