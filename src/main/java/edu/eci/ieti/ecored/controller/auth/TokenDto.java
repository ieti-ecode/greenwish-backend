package edu.eci.ieti.ecored.controller.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class TokenDto {

    private String token;
    private Date expirationDate;
}
