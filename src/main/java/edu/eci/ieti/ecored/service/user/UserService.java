package edu.eci.ieti.ecored.service.user;

import java.util.List;
import java.util.Optional;

import edu.eci.ieti.ecored.controller.user.UserDto;
import edu.eci.ieti.ecored.repository.document.User;

public interface UserService {

    User save(UserDto user);

    Optional<User> findById(String id);

    List<User> all();

    void deleteById(String id);

    void update(UserDto user, String id);

}
