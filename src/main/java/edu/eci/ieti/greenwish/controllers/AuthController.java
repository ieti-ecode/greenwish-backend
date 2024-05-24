package edu.eci.ieti.greenwish.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.ieti.greenwish.models.dto.LoginDto;
import edu.eci.ieti.greenwish.models.dto.TokenDto;
import edu.eci.ieti.greenwish.services.AuthService;
import lombok.RequiredArgsConstructor;

/**
 * The AuthController class handles authentication-related operations.
 * It provides endpoints for user login and token generation.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Signs in a user with the provided login credentials.
     *
     * @param loginDto The login credentials of the user.
     * @return A ResponseEntity containing the token DTO if the sign-in is
     *         successful, or an appropriate error response if it fails.
     */
    @PostMapping("/signin")
    public ResponseEntity<TokenDto> signIn(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.signIn(loginDto));
    }

    /**
     * Signs out the user by invalidating the provided JWT token.
     *
     * @param jwt the JWT token obtained during authentication
     * @return a ResponseEntity containing the token DTO
     */
    @PostMapping("/signout")
    public ResponseEntity<TokenDto> signOut(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        return ResponseEntity.ok(authService.signOut(jwt));
    }

}
