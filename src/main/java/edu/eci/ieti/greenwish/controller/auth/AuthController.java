package edu.eci.ieti.greenwish.controller.auth;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.ieti.greenwish.exception.InvalidCredentialsException;
import edu.eci.ieti.greenwish.repository.document.User;
import edu.eci.ieti.greenwish.security.jwt.JwtUtils;
import edu.eci.ieti.greenwish.service.user.UserServiceMongoDB;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final JwtUtils jwtService;
    private final UserServiceMongoDB userService;

    public AuthController(JwtUtils jwtService, UserServiceMongoDB userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        Optional<User> userOptional = userService.findByEmail(loginDto.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (BCrypt.checkpw(loginDto.getPassword(), user.getPasswordHash())) {
                TokenDto tokenDto = jwtService.generateTokenDto(user);
                return ResponseEntity.ok(tokenDto);
            } else {
                throw new InvalidCredentialsException("Invalid login");
            }
        } else {
            throw new InvalidCredentialsException("Invalid login");
        }
    }
}
