package edu.eci.ieti.greenwish.repository.document;

import edu.eci.ieti.greenwish.controller.company.CompanyDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
public class Company {

    @Id
    private String id;
    private String name;
    private String description;
    private String phoneNumber;
    private String address;
    private String oppeningHours;

    public Company(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Company(CompanyDto companyDto) {
        this.name = companyDto.getName();
        this.description = companyDto.getDescription();
        this.phoneNumber = companyDto.getPhoneNumber();
        this.address = companyDto.getAddress();
        this.oppeningHours = companyDto.getOppeningHours();
    }

}
