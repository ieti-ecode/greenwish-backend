package edu.eci.ieti.greenwish.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.eci.ieti.greenwish.exceptions.UserNotFoundException;
import edu.eci.ieti.greenwish.models.Role;
import edu.eci.ieti.greenwish.models.User;
import edu.eci.ieti.greenwish.models.dto.UserDto;
import edu.eci.ieti.greenwish.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

/**
 * This class implements the UserService interface using MongoDB as the data
 * source.
 * It provides methods to save, retrieve, update, and delete User objects.
 */
@Service
@RequiredArgsConstructor
public class UserService implements CrudService<User, UserDto, String, UserNotFoundException> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(UserDto userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .passwordHash(passwordEncoder.encode(userDto.getPassword()))
                .role(Role.CUSTOMER.getName())
                .points(0)
                .build();
        if (userDto.isCompany())
            user.setRole(Role.COMPANY.getName());
        return userRepository.save(user);
    }

    @Override
    public void update(UserDto userDto, String id) throws UserNotFoundException {
        User userToUpdate = findById(id);
        String role = userDto.isCompany() ? Role.COMPANY.getName() : Role.CUSTOMER.getName();
        userToUpdate.setName(userDto.getName());
        userToUpdate.setEmail(userDto.getEmail());
        userToUpdate.setPasswordHash(passwordEncoder.encode(userDto.getPassword()));
        userToUpdate.setRole(role);
        userRepository.save(userToUpdate);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            throw new UserNotFoundException();
        return optionalUser.get();
    }

    public User findByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty())
            throw new UserNotFoundException();
        return optionalUser.get();
    }

    @Override
    public void deleteById(String id) throws UserNotFoundException {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException();
        userRepository.deleteById(id);
    }

}
