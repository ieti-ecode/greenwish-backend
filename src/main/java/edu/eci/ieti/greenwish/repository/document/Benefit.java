package edu.eci.ieti.greenwish.repository.document;

import edu.eci.ieti.greenwish.controller.benefit.BenefitDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    public Benefit(BenefitDto benefitDto) {
        this.name = benefitDto.getName();
        this.value = benefitDto.getValue();
        this.description = benefitDto.getDescription();
    }

}
