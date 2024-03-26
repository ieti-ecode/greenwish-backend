package edu.eci.ieti.greenwish.exception;

public class CompanyNotFoundException extends RuntimeException {

    public CompanyNotFoundException() {
        super("Company not found");
    }

}
