package edu.eci.ieti.greenwish.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import edu.eci.ieti.greenwish.exceptions.UserNotFoundException;
import edu.eci.ieti.greenwish.exceptions.UserNotMatchException;
import edu.eci.ieti.greenwish.models.domain.Role;
import edu.eci.ieti.greenwish.models.domain.User;
import edu.eci.ieti.greenwish.models.dto.PointsDto;
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
        if (Boolean.TRUE.equals(userDto.getIsCompany()))
            user.setRole(Role.COMPANY.getName());
        try {
            user.setImage(new Binary(BsonBinarySubType.BINARY, loadDefaultImageBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userRepository.save(user);
    }

    @Override
    public void update(UserDto userDto, String id) throws UserNotFoundException, UserNotMatchException {
        User userToUpdate = findById(id);
        if (!userToUpdate.getId().equals(id))
            throw new UserNotMatchException();
        String role = Boolean.TRUE.equals(userDto.getIsCompany()) ? Role.COMPANY.getName() : Role.CUSTOMER.getName();
        userToUpdate.setName(userDto.getName());
        userToUpdate.setEmail(userDto.getEmail());
        userToUpdate.setPasswordHash(passwordEncoder.encode(userDto.getPassword()));
        userToUpdate.setRole(role);
        userRepository.save(userToUpdate);
    }

    public void updatePoints(PointsDto pointsDto, String id) {
        User userToUpdate = findById(id);
        if (!userToUpdate.getId().equals(id))
            throw new UserNotMatchException();
        userToUpdate.setPoints(pointsDto.getPoints());
        userRepository.save(userToUpdate);
    }

    public void uploadImage(String id, MultipartFile image) throws UserNotFoundException, IOException {
        User user = findById(id);
        user.setImage(new Binary(BsonBinarySubType.BINARY, image.getBytes()));
        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            throw new UserNotFoundException(id);
        return optionalUser.get();
    }

    public User findByEmail(String email) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty())
            throw new UserNotFoundException(email);
        return optionalUser.get();
    }

    public byte[] getImage(String id) {
        User user = findById(id);
        return user.getImage().getData();
    }

    public byte[] loadDefaultImageBytes() throws IOException {
        Path path = Paths.get(ResourceUtils.getFile("classpath:static/default-profile.png").toURI());
        return Files.readAllBytes(path);
    }

    @Override
    public void deleteById(String id) throws UserNotFoundException {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException(id);
        userRepository.deleteById(id);
    }

    public boolean existsAdminUser() {
        Optional<User> optionalUser = userRepository.findByRole(Role.ADMINISTRATOR.getName());
        return optionalUser.isPresent();
    }

}
