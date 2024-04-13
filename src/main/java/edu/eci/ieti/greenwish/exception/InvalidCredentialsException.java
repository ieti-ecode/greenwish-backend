package edu.eci.ieti.greenwish.exception;

/**
 * Exception thrown when the provided credentials (username or password) are
 * invalid.
 */
public class InvalidCredentialsException extends RuntimeException {

    /**
     * Constructs a new InvalidCredentialsException with the specified detail
     * message.
     *
     * @param message the detail message
     */
    public InvalidCredentialsException(String message) {
        super("Invalid username or password");
    }

}
