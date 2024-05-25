package edu.eci.ieti.greenwish.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when the user does not match.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserNotMatchException extends RuntimeException {

    /**
     * Constructs a new UserNotMatchException with a default error message.
     */
    public UserNotMatchException() {
        super("User do not match");
    }

}
