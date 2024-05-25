package edu.eci.ieti.greenwish.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the provided credentials (username or password) are
 * invalid.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidCredentialsException extends RuntimeException {

    /**
     * Constructs a new InvalidCredentialsException.
     */
    public InvalidCredentialsException() {
        super("Invalid username or password");
    }

}
