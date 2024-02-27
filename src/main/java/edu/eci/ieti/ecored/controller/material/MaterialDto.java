package edu.eci.ieti.ecored.controller.material;

public class MaterialDto {
    private String name;

    private String description;

    private int kiloValue;

    public MaterialDto() {
    }

    public MaterialDto(String name, String description, int kiloValue) {
        this.name = name;
        this.description = description;
        this.kiloValue = kiloValue;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getKiloValue() {
        return kiloValue;
    }
}
