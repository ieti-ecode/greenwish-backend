package edu.eci.ieti.greenwish.repository.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

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

}
