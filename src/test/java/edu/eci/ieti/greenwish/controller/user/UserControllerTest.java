package edu.eci.ieti.greenwish.controller.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;

import edu.eci.ieti.greenwish.repository.document.User;
import edu.eci.ieti.greenwish.service.user.UserService;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void all() {
        // Arrange
        User user1 = new User("1", "Pepe", "pepe@pepe.com", "1234");
        User user2 = new User("2", "Juan", "juan@juan.com", "5678");
        when(userService.all()).thenReturn(Arrays.asList(user1, user2));
        // Act
        ResponseEntity<List<User>> response = userController.all();
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertTrue(BCrypt.checkpw("5678", response.getBody().get(1).getPasswordHash()));
    }

    @Test
    void findById() {
        // Arrange
        String userId = "1";
        User user = new User("1", "Pepe", "pepe@pepe.com", "1234");
        // Act
        
        // Assert
    }

    @Test
    void create() {
        // Arrange
        UserDto userDto = new UserDto("Pepe", "pepe@pepe.com", "1234");
        User user = new User(userDto);
        when(userService.save(userDto)).thenReturn(user);
        // Act
        ResponseEntity<User> response = userController.create(userDto);
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void update() {
        // Arrange
        String userId = "1";
        UserDto userDto = new UserDto("Pepe", "pepe@pepe.com", "5678");

    }

    @Test
    void deleteUser() {
    }
}