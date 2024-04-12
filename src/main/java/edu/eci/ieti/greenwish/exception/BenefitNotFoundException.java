package edu.eci.ieti.greenwish.exception;

public class BenefitNotFoundException extends RuntimeException {

    public BenefitNotFoundException() {
        super("Benefit not found");
    }

}
