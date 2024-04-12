package edu.eci.ieti.greenwish.service.user;

import edu.eci.ieti.greenwish.controller.user.UserDto;
import edu.eci.ieti.greenwish.exception.BenefitNotFoundException;
import edu.eci.ieti.greenwish.exception.UserNotFoundException;
import edu.eci.ieti.greenwish.repository.UserRepository;
import edu.eci.ieti.greenwish.repository.document.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceMongoDBTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceMongoDB userServiceMongoDB;

    @Test
    void testFindAllUsers() {
        List<User> users = Arrays.asList(
                new User("Pepe", "pepe@pepe.com", "1234"),
                new User("Juan", "juan@juan.com", "5678"));
        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<User> allUsers = userServiceMongoDB.all();
        Assertions.assertNotNull(allUsers);
        assertEquals(2, allUsers.size());
        assertEquals(users, allUsers);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdExistingUser() throws BenefitNotFoundException {
        String id = "1";
        UserDto userDto = new UserDto("Pepe", "pepe@pepe.com", "1234");
        User benefit = new User(userDto);
        when(userRepository.findById(id)).thenReturn(Optional.of(benefit));
        User foundUser = userServiceMongoDB.findById(id);
        verify(userRepository, times(1)).findById(id);
        assertEquals(benefit, foundUser);
    }

    @Test
    void testFindByIdNotFound() {
        String id = "1";
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userServiceMongoDB.findById(id));
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void testSaveNewUser() {
        UserDto userDto = new UserDto("Pepe", "pepe@pepe.com", "1234");
        User user = new User(userDto);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User savedUser = userServiceMongoDB.save(userDto);
        Assertions.assertNotNull(savedUser);
        verify(userRepository, times(1)).save(user);
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    void testUpdateExistingUser() {
        String id = "1";
        UserDto userDto = new UserDto("Pepe", "pepe@pepe.com", "1234");
        User user = new User("Juan", "juan@juan.com", "5678");
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        userServiceMongoDB.update(userDto, id);
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(user);
        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getEmail(), user.getEmail());
    }

    @Test
    void testUpdateNotExistingUser() {
        String id = "1";
        UserDto userDto = new UserDto("Pepe", "pepe@pepe.com", "1234");
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userServiceMongoDB.update(userDto, id));
        verify(userRepository, times(1)).findById(id);
    }


    @Test
    void testDeleteExistingUser() {
        String id = "1";
        when(userRepository.existsById(id)).thenReturn(true);
        userServiceMongoDB.deleteById(id);
        verify(userRepository, times(1)).deleteById(id);
        verify(userRepository, times(1)).existsById(id);
    }

    @Test
    void testDeleteNotExistingUser() {
        String id = "1";
        when(userRepository.existsById(id)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> userServiceMongoDB.deleteById(id));
        verify(userRepository, times(1)).existsById(id);
    }

}
