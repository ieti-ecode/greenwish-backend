package edu.eci.ieti.ecored.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message) {
        super("Invalid username or password");
    }

}
