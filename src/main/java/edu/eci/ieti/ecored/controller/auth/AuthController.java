package edu.eci.ieti.ecored.controller.auth;

import edu.eci.ieti.ecored.exception.InvalidCredentialsException;
import edu.eci.ieti.ecored.repository.UserRepository;
import edu.eci.ieti.ecored.repository.document.User;
import edu.eci.ieti.ecored.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final JwtUtils jwtService;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(JwtUtils jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        Optional<User> userOptional = userRepository.findByEmail(loginDto.getEmail());
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
