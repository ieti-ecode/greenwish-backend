package edu.eci.ieti.greenwish.exceptions;

/**
 * Exception thrown when the provided credentials (username or password) are
 * invalid.
 */
public class InvalidCredentialsException extends RuntimeException {

    /**
     * Constructs a new InvalidCredentialsException.
     */
    public InvalidCredentialsException() {
        super("Invalid username or password");
    }

}
