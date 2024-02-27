package edu.eci.ieti.ecored.repository.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
public class Benefit {

    @Id
    private String id;
    private String name;
    private String description;
    private int value;

    public Benefit(String name, int value) {
        this.name = name;
        this.value = value;
    }

}
