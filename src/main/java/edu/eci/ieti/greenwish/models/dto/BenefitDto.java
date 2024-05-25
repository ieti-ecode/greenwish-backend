package edu.eci.ieti.greenwish.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a benefit data transfer object (DTO) used for transferring benefit
 * information.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BenefitDto {

    private String name;
    private String description;
    private int value;

}
