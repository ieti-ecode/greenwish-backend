package edu.eci.ieti.greenwish.service.user;

import java.util.List;
import java.util.Optional;

import edu.eci.ieti.greenwish.controller.user.UserDto;
import edu.eci.ieti.greenwish.repository.document.User;

public interface UserService {

    User save(UserDto user);

    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);

    List<User> all();

    void deleteById(String id);

    void update(UserDto user, String id);

}
