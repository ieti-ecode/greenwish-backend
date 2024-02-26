package edu.eci.ieti.ecored.controller.user;

import lombok.Getter;

@Getter
public class UserDto {
    private String name;

    private String email;

    private String password;

    public UserDto() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}