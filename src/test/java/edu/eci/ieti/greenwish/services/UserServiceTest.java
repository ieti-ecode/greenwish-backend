package edu.eci.ieti.greenwish.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.eci.ieti.greenwish.exceptions.UserNotFoundException;
import edu.eci.ieti.greenwish.models.domain.Role;
import edu.eci.ieti.greenwish.models.domain.User;
import edu.eci.ieti.greenwish.models.dto.UserDto;
import edu.eci.ieti.greenwish.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private List<User> users;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("1", "Pepe", "pepe@pepe.com", "1234", Role.CUSTOMER.getName(), 0, null);
        User user2 = new User("2", "Juan", "juan@juan.com", "5678", Role.CUSTOMER.getName(), 0, null);
        users = List.of(user, user2);
    }

    @Test
    void testFindAllUsers() {
        when(userRepository.findAll()).thenReturn(users);
        List<User> allUsers = userService.findAll();
        assertNotNull(allUsers);
        assertEquals(2, allUsers.size());
        assertEquals(users, allUsers);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdExistingUser() throws UserNotFoundException {
        String id = "1";
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        User foundUser = userService.findById(id);
        verify(userRepository, times(1)).findById(id);
        assertEquals(user, foundUser);
    }

    @Test
    void testFindByIdNotFound() {
        String id = "1";
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findById(id));
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void testSaveNewUser() {
        UserDto userDto = new UserDto("Pepe", "pepe@pepe.com", "1234", false);
        User user = new User(null, "Pepe", "pepe@pepe.com", null, Role.CUSTOMER.getName(), 0, null);
        when(userRepository.save(user)).thenReturn(user);
        User savedUser = userService.save(userDto);
        assertNotNull(savedUser);
        verify(userRepository, times(1)).save(user);
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    void testUpdateExistingUser() {
        String id = "1";
        UserDto userDto = new UserDto("Pepe", "pepe@pepe.com", "1234", false);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        userService.update(userDto, id);
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(user);
        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getEmail(), user.getEmail());
    }

    @Test
    void testUpdateNotExistingUser() {
        String id = "1";
        UserDto userDto = new UserDto("Pepe", "pepe@pepe.com", "1234", false);
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.update(userDto, id));
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteExistingUser() {
        String id = "1";
        when(userRepository.existsById(id)).thenReturn(true);
        userService.deleteById(id);
        verify(userRepository, times(1)).deleteById(id);
        verify(userRepository, times(1)).existsById(id);
    }

    @Test
    void testDeleteNotExistingUser() {
        String id = "1";
        when(userRepository.existsById(id)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> userService.deleteById(id));
        verify(userRepository, times(1)).existsById(id);
    }

}
