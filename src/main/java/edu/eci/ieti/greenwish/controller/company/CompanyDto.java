package edu.eci.ieti.greenwish.controller.company;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompanyDto {

    private String name;
    private String description;
    private String phoneNumber;
    private String address;
    private String oppeningHours;

    public CompanyDto(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

}
