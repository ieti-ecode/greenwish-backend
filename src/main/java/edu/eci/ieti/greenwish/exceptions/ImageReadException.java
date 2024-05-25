package edu.eci.ieti.greenwish.exceptions;

/**
 * Exception thrown when there is an error reading an image.
 */
public class ImageReadException extends RuntimeException {

    /**
     * Constructs a new ImageReadException with the specified detail message.
     *
     * @param message the detail message
     */
    public ImageReadException(String message) {
        super(message);
    }

}
