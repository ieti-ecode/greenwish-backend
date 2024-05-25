package edu.eci.ieti.greenwish.models.domain;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "benefits")
public class Benefit {

    @Id
    private String id;
    private String name;
    private String description;
    private int value;
    private Binary image;

}
