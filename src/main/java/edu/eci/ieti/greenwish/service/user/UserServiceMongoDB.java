package edu.eci.ieti.greenwish.service.user;

import edu.eci.ieti.greenwish.controller.user.UserDto;
import edu.eci.ieti.greenwish.exception.UserNotFoundException;
import edu.eci.ieti.greenwish.repository.UserRepository;
import edu.eci.ieti.greenwish.repository.document.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceMongoDB implements UserService {

    private final UserRepository userRepository;

    public UserServiceMongoDB(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserDto userDto) {
        return userRepository.save(new User(userDto));
    }

    @Override
    public User findById(String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) throw new UserNotFoundException();
        return optionalUser.get();
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) throw new UserNotFoundException();
        return optionalUser.get();
    }

    @Override
    public List<User> all() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        if (!userRepository.existsById(id)) throw new UserNotFoundException();
        userRepository.deleteById(id);
    }

    @Override
    public void update(UserDto userDto, String id) {
        User userToUpdate = findById(id);
        userToUpdate.setName(userDto.getName());
        userToUpdate.setEmail(userDto.getEmail());
        userToUpdate.setPasswordHash(userDto.getPassword());
        userRepository.save(userToUpdate);
    }
}
