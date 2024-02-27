package edu.eci.ieti.ecored.controller.user;

import lombok.Getter;

@Getter
public class UserDto {
    private String name;

    private String email;

    private String id;

    private String password;

    public UserDto() {
    }

    public UserDto(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
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

    public String getId() {
        return id;
    }

}