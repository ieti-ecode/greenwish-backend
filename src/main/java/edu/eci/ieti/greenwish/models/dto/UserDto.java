package edu.eci.ieti.greenwish.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a user data transfer object (DTO) that contains information about
 * a user.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String name;
    private String email;
    private String password;
    private boolean isCompany;

}