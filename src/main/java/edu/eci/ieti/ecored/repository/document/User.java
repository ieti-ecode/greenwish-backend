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

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        this.points = 0;
    }

    public User(UserDto userDto) {
        this.id = null;
        this.name = userDto.getName();
        this.email = userDto.getEmail();
        this.points = 0;
        this.passwordHash = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());
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

    public void setPasswordHash(String password) {
        this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
    }
}