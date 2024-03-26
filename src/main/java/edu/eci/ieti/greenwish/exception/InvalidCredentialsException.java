package edu.eci.ieti.greenwish.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message) {
        super("Invalid username or password");
    }

}
