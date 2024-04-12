package edu.eci.ieti.greenwish.repository.document;

import edu.eci.ieti.greenwish.controller.material.MaterialDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
public class Material {

    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String description;
    private int kiloValue;

    public Material(String name, String description, int kiloValue) {
        this.name = name;
        this.description = description;
        this.kiloValue = kiloValue;
    }

    public Material(MaterialDto materialDto) {
        this.name = materialDto.getName();
        this.description = materialDto.getDescription();
        this.kiloValue = materialDto.getKiloValue();
    }
}
