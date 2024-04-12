package edu.eci.ieti.greenwish.exception;

public class MaterialNotFoundException extends RuntimeException {

    public MaterialNotFoundException() {
        super("Material not found");
    }

}
