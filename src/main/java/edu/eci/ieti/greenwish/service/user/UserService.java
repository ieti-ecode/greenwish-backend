package edu.eci.ieti.greenwish.service.user;

import edu.eci.ieti.greenwish.controller.user.UserDto;
import edu.eci.ieti.greenwish.exception.UserNotFoundException;
import edu.eci.ieti.greenwish.repository.document.User;

import java.util.List;

public interface UserService {

    User save(UserDto user);

    User findById(String id) throws UserNotFoundException;

    User findByEmail(String email) throws UserNotFoundException;

    List<User> all();

    void deleteById(String id) throws UserNotFoundException;

    void update(UserDto user, String id) throws UserNotFoundException;

}
