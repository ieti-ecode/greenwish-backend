package edu.eci.ieti.greenwish.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a material data transfer object (DTO).
 * This class is used to transfer material information between different layers
 * of the application.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDto {

    private String name;
    private String description;
    private int kiloValue;

}
