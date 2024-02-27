package edu.eci.ieti.ecored.repository.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.eci.ieti.ecored.controller.material.MaterialDto;

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

    public Material(String id, String name, String description, int kiloValue) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.kiloValue = kiloValue;
    }

    public Material(MaterialDto materialDto) {
        this.name = materialDto.getName();
        this.description = materialDto.getDescription();
        this.kiloValue = materialDto.getKiloValue();
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


