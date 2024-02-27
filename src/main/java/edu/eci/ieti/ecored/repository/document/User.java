package edu.eci.ieti.ecored.repository.document;

import edu.eci.ieti.ecored.controller.user.UserDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Document
public class User {

    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String email;

    private String passwordHash;

    private int points;

    public User() {

    }

    public User(UserDto userDto) {
        name = userDto.getName();
        email = userDto.getEmail();
        points = 0;
        passwordHash = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPoints() {
        return points;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}