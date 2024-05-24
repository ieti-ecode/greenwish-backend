package edu.eci.ieti.greenwish.exceptions;

/**
 * Exception thrown when a user is not found.
 */
public class UserNotFoundException extends ResourceNotFoundException {

    /**
     * Constructs a new UserNotFoundException with a default error message.
     */
    public UserNotFoundException(Object fieldValue) {
        super("User", "id", fieldValue);
    }

}
