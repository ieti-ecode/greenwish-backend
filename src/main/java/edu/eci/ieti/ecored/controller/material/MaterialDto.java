package edu.eci.ieti.ecored.controller.material;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDto {

    private String name;
    private String description;
    private int kiloValue;

}
