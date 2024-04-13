package edu.eci.ieti.greenwish.controller.auth;

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
