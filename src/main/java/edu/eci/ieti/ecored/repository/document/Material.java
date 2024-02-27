package edu.eci.ieti.ecored.repository.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Material {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String description;

    private int kiloValue;

    public Material() {
    }

    public Material(String name, int kiloValue) {
        this.name = name;
        this.kiloValue = kiloValue;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getKiloValue() {
        return kiloValue;
    }

    public void setKiloValue(int kiloValue) {
        this.kiloValue = kiloValue;
    }
}


