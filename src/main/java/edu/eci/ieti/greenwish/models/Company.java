package edu.eci.ieti.greenwish.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    private String id;
    private String name;
    private String description;
    private String phoneNumber;
    private String address;
    private String oppeningHours;

}
