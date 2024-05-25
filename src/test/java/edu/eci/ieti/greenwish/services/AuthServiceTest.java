package edu.eci.ieti.greenwish.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.eci.ieti.greenwish.exceptions.InvalidCredentialsException;
import edu.eci.ieti.greenwish.exceptions.UserNotFoundException;
import edu.eci.ieti.greenwish.models.domain.Role;
import edu.eci.ieti.greenwish.models.domain.User;
import edu.eci.ieti.greenwish.models.dto.LoginDto;
import edu.eci.ieti.greenwish.models.dto.TokenDto;
import edu.eci.ieti.greenwish.repositories.UserRepository;
import edu.eci.ieti.greenwish.security.JwtUtils;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void signIn() {
        LoginDto loginDto = new LoginDto("pepe@pepe.com", "1234");
        TokenDto tokenDto = new TokenDto("THE_TOKEN", null);
        User user = new User("1", "Pepe", "pepe@pepe.com", "1234", Role.CUSTOMER.getName(), 0, null);
        when(userRepository.findByEmail("pepe@pepe.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(jwtUtils.generateAccessToken(user)).thenReturn("THE_TOKEN");
        assertEquals(tokenDto.getToken(), authService.signIn(loginDto).getToken());
    }

    @Test
    void signInUserNotFound() {
        LoginDto loginDto = new LoginDto("pepe@pepe.com", "1234");
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> authService.signIn(loginDto));
    }

    @Test
    void signInInvalidCredentials() {
        LoginDto loginDto = new LoginDto("pepe@pepe.com", "INVALID");
        User user = new User("1", "Pepe", "pepe@pepe.com", "1234", Role.CUSTOMER.getName(), 0, null);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(false);
        assertThrows(InvalidCredentialsException.class, () -> authService.signIn(loginDto));
    }

    @Test
    void signOut() {
        String jwt = "THE_TOKEN";
        TokenDto token = new TokenDto(jwt, null);
        TokenDto message = new TokenDto("Removed session", null);
        when(jwtUtils.deleteAccessToken(token.getToken())).thenReturn(message.getToken());
        assertEquals(message.getToken(), authService.signOut(token.getToken()).getToken());
    }

}
