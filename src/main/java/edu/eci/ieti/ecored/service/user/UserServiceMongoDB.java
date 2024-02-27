package edu.eci.ieti.ecored.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.ieti.ecored.controller.user.UserDto;
import edu.eci.ieti.ecored.repository.UserRepository;
import edu.eci.ieti.ecored.repository.document.User;

@Service
public class UserServiceMongoDB implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceMongoDB(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserDto user) {
        return userRepository.save(new User(user.getId(), user.getName(), user.getEmail(), user.getPassword()));
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
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
        User userToUpdate = userRepository.findById(id).get();
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPasswordHash(user.getPassword());
    }
    
}
