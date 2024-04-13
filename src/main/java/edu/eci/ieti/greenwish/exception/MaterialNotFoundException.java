package edu.eci.ieti.greenwish.exception;

/**
 * Exception thrown when a material is not found.
 */
public class MaterialNotFoundException extends RuntimeException {

    /**
     * Constructs a new MaterialNotFoundException with a default error message.
     */
    public MaterialNotFoundException() {
        super("Material not found");
    }

}
