package edu.eci.ieti.ecored.exception;

public class MaterialNotFoundException extends RuntimeException {

    public MaterialNotFoundException() {
        super("Material not found");
    }

}
