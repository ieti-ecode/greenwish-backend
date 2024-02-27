package edu.eci.ieti.ecored.controller.auth;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenDto {

    private String token;
    private Date expirationDate;

}
