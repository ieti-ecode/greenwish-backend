package edu.eci.ieti.ecored.controller.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginDto {

    private String email;
    private String password;

}
