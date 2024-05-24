package edu.eci.ieti.greenwish.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a Data Transfer Object (DTO) for a token and its expiration date.
 */
@Getter
@AllArgsConstructor
public class TokenDto {

    private String token;

}
