package edu.eci.ieti.ecored.controller.benefit;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BenefitDto {

    private String name;
    private String description;
    private int value;

    public BenefitDto(String name, int value) {
        this.name = name;
        this.value = value;
    }

}
