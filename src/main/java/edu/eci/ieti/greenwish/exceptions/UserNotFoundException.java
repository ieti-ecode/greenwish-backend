package edu.eci.ieti.greenwish.exceptions;

/**
 * Exception thrown when a user is not found.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException with a default error message.
     */
    public UserNotFoundException() {
        super("User not found");
    }

}
