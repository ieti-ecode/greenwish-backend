package edu.eci.ieti.greenwish.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.eci.ieti.greenwish.exceptions.InvalidCredentialsException;
import edu.eci.ieti.greenwish.exceptions.UserNotFoundException;
import edu.eci.ieti.greenwish.models.User;
import edu.eci.ieti.greenwish.models.dto.LoginDto;
import edu.eci.ieti.greenwish.models.dto.TokenDto;
import edu.eci.ieti.greenwish.repositories.UserRepository;
import edu.eci.ieti.greenwish.security.JwtUtils;
import lombok.RequiredArgsConstructor;

/**
 * This class provides authentication services for the application.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    /**
     * Signs in a user with the given credentials.
     * 
     * @param loginDto the login information.
     * @return a token for the user.
     * @throws UserNotFoundException       if the user is not found.
     * @throws InvalidCredentialsException if the credentials are invalid.
     */
    public TokenDto signIn(LoginDto loginDto) throws UserNotFoundException, InvalidCredentialsException {
        Optional<User> user = userRepository.findByEmail(loginDto.getEmail());
        if (user.isEmpty())
            throw new UserNotFoundException();
        if (!passwordEncoder.matches(loginDto.getPassword(), user.get().getPasswordHash()))
            throw new InvalidCredentialsException();
        return new TokenDto(jwtUtils.generateAccessToken(user.get()));
    }

    /**
     * Signs out a user with the given token.
     * 
     * @param jwt the token to sign out.
     * @return the signed out token.
     */
    public TokenDto signOut(String jwt) {
        return new TokenDto(jwtUtils.deleteAccessToken(jwt));
    }

}
