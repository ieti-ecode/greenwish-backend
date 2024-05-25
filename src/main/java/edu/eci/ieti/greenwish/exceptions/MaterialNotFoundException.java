package edu.eci.ieti.greenwish.exceptions;

/**
 * Exception thrown when a material is not found.
 */
public class MaterialNotFoundException extends ResourceNotFoundException {

    /**
     * Constructs a new MaterialNotFoundException with a default error message.
     */
    public MaterialNotFoundException(Object fieldValue) {
        super("Material", "id", fieldValue);
    }

}
