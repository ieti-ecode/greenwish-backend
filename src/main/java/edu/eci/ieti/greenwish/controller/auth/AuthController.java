package edu.eci.ieti.greenwish.controller.auth;

import edu.eci.ieti.greenwish.exception.UserNotFoundException;
import edu.eci.ieti.greenwish.repository.document.User;
import edu.eci.ieti.greenwish.security.jwt.JwtUtils;
import edu.eci.ieti.greenwish.service.user.UserServiceMongoDB;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The AuthController class handles authentication-related operations.
 * It provides endpoints for user login and token generation.
 */
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final JwtUtils jwtService;
    private final UserServiceMongoDB userService;

    /**
     * Constructs a new AuthController with the specified JwtUtils and
     * UserServiceMongoDB instances.
     *
     * @param jwtService  the JwtUtils instance used for JWT token generation and
     *                    validation
     * @param userService the UserServiceMongoDB instance used for user-related
     *                    operations
     */
    public AuthController(JwtUtils jwtService, UserServiceMongoDB userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    /**
     * Authenticates a user by checking their credentials and generating a JWT
     * token.
     *
     * @param loginDto The login credentials of the user.
     * @return A ResponseEntity containing the generated JWT token.
     */
    @PostMapping
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        try {
            User user = userService.findByEmail(loginDto.getEmail());
            if (user != null && BCrypt.checkpw(loginDto.getPassword(), user.getPasswordHash())) {
                TokenDto tokenDto = jwtService.generateTokenDto(user);
                return ResponseEntity.ok(tokenDto);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
