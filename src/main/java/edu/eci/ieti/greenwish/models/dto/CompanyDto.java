package edu.eci.ieti.greenwish.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a company data transfer object.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private String name;
    private String description;
    private String phoneNumber;
    private String address;

}
