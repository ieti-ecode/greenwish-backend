package edu.eci.ieti.greenwish.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a company data transfer object.
 */
@Getter
@NoArgsConstructor
public class CompanyDto {

    private String name;
    private String description;
    private String phoneNumber;
    private String address;
    private String oppeningHours;

    /**
     * Constructs a new CompanyDto object with the specified name, phone number, and
     * address.
     *
     * @param name        the name of the company
     * @param phoneNumber the phone number of the company
     * @param address     the address of the company
     */
    public CompanyDto(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

}
