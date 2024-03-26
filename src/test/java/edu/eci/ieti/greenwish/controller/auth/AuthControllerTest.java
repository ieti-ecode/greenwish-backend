package edu.eci.ieti.greenwish.controller.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.eci.ieti.greenwish.repository.document.User;
import edu.eci.ieti.greenwish.security.jwt.JwtUtils;
import edu.eci.ieti.greenwish.service.user.UserServiceMongoDB;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

class AuthControllerTest {

    @Mock
    private JwtUtils jwtService;

    @Mock
    private UserServiceMongoDB userService;

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
        // Arrange
        User user = new User("1", "Pepe", "pepe@pepe.com", "1234");
        Date expirationDate = new Date(System.currentTimeMillis() + Long.parseLong(expiration));
        when(userService.findByEmail("pepe@pepe.com")).thenReturn(Optional.of(user));
        String token = Jwts.builder().subject(user.getId())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(expirationDate)
                    .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .compact();


        TokenDto tokenDto = new TokenDto(token, expirationDate);
        when(jwtService.generateTokenDto(user)).thenReturn(tokenDto);

        LoginDto loginDto = new LoginDto("pepe@pepe.com", "1234");
        // Act
        ResponseEntity<TokenDto> response = authController.login(loginDto);
        // Assert
        assertEquals(tokenDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}