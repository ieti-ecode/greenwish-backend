package edu.eci.ieti.ecored.controller.benefit;

public class BenefitDto {

    private String name;

    private String description;

    private int value;

    public BenefitDto(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public BenefitDto() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }
}
