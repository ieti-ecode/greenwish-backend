package edu.eci.ieti.greenwish.models.domain;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "materials")
public class Material {

    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String description;
    private int kiloValue;
    private Binary image;

}
