package edu.eci.ieti.greenwish.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a data transfer object for login information.
 */
@Getter
@AllArgsConstructor
public class LoginDto {

    private String email;
    private String password;

}
