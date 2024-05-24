package edu.eci.ieti.greenwish.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.eci.ieti.greenwish.exceptions.InvalidCredentialsException;
import edu.eci.ieti.greenwish.exceptions.UserNotFoundException;
import edu.eci.ieti.greenwish.models.Role;
import edu.eci.ieti.greenwish.models.User;
import edu.eci.ieti.greenwish.models.dto.LoginDto;
import edu.eci.ieti.greenwish.models.dto.TokenDto;
import edu.eci.ieti.greenwish.services.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private String secret;
    private String expiration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        secret = "jOpQvlhMhORu24CyoBAlYMsLxA9vgs8graMJhaJ02yZpVfQOeLVnsmFsuhdyqSoY";
        expiration = "3600000";
    }

    @Test
    void login() {
        User user = new User("1", "Pepe", "pepe@pepe.com", "1234", Role.CUSTOMER.getName(), 0);
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + Long.parseLong(expiration));
        String token = Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
        LoginDto loginDto = new LoginDto("pepe@pepe.com", "1234");
        TokenDto tokenDto = new TokenDto(token);
        when(authService.signIn(loginDto)).thenReturn(tokenDto);
        ResponseEntity<TokenDto> response = authController.signIn(loginDto);
        assertEquals(tokenDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void loginInvalidCredentials() {
        LoginDto loginDto = new LoginDto("pepe@pepe.com", "1234");
        when(authService.signIn(loginDto)).thenThrow(new InvalidCredentialsException());
        ResponseEntity<TokenDto> response = authController.signIn(loginDto);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void loginUserNotFound() {
        LoginDto loginDto = new LoginDto("pepe@pepe.com", "1234");
        when(authService.signIn(loginDto)).thenThrow(new UserNotFoundException());
        ResponseEntity<TokenDto> response = authController.signIn(loginDto);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void signOut() {
        String token = "Bearer XXXXXXX";
        TokenDto tokenDto = new TokenDto("Removed session");
        when(authService.signOut(token)).thenReturn(tokenDto);
        ResponseEntity<TokenDto> response = authController.signOut(token);
        assertEquals(tokenDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
