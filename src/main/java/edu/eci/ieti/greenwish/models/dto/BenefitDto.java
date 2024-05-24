package edu.eci.ieti.greenwish.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a benefit data transfer object (DTO) used for transferring benefit
 * information.
 */
@Getter
@NoArgsConstructor
public class BenefitDto {

    private String name;
    private String description;
    private int value;

    /**
     * Constructs a new BenefitDto object with the specified name and value.
     *
     * @param name  the name of the benefit
     * @param value the value of the benefit
     */
    public BenefitDto(String name, int value) {
        this.name = name;
        this.value = value;
    }

}
