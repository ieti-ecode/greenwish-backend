package edu.eci.ieti.greenwish.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.eci.ieti.greenwish.controller.user.UserDto;
import edu.eci.ieti.greenwish.exception.UserNotFoundException;
import edu.eci.ieti.greenwish.repository.UserRepository;
import edu.eci.ieti.greenwish.repository.document.User;

@Service
public class UserServiceMongoDB implements UserService {

    private final UserRepository userRepository;

    public UserServiceMongoDB(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserDto user) {
        return userRepository.save(new User(user));
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> all() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public void update(UserDto user, String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User userToUpdate = optionalUser.get();
            userToUpdate.setName(user.getName());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setPasswordHash(user.getPassword());
        } else {
            throw new UserNotFoundException();
        }
    }

}