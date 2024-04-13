package edu.eci.ieti.greenwish.security.jwt;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

import javax.crypto.SecretKey;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import edu.eci.ieti.greenwish.controller.auth.TokenDto;
import edu.eci.ieti.greenwish.repository.document.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

/**
 * Utility class for handling JSON Web Tokens (JWT).
 * This class provides methods for generating and parsing JWTs, as well as
 * retrieving claims from JWTs.
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtUtils {

    private String secret;
    private String expiration;

    /**
     * Generates a TokenDto object containing an access token and its expiration
     * date.
     *
     * @param user The user for whom the token is being generated.
     * @return The TokenDto object containing the access token and expiration date.
     */
    public TokenDto generateTokenDto(User user) {
        Date expirationDate = new Date(System.currentTimeMillis() + Long.parseLong(expiration));
        return new TokenDto(generateAccessToken(user, expirationDate), expirationDate);
    }

    /**
     * Generates an access token for the given user with the specified expiration
     * date.
     *
     * @param user           The user for whom the access token is generated.
     * @param expirationDate The expiration date of the access token.
     * @return The generated access token as a string.
     */
    public String generateAccessToken(User user, Date expirationDate) {
        return Jwts.builder()
                .subject(user.getId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expirationDate)
                .signWith(getSignatureKey())
                .compact();
    }

    /**
     * Retrieves the subject from the given JWT token.
     *
     * @param token The JWT token from which to retrieve the subject.
     * @return The subject extracted from the token.
     */
    public String getSubjectFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Retrieves the roles from the given JWT token.
     *
     * @param token The JWT token from which to retrieve the roles.
     * @return The list of roles extracted from the token.
     */
    public List<String> getRolesFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("roles", List.class));
    }

    /**
     * Retrieves a specific claim from the given JWT token.
     *
     * @param <T>            the type of the claim to retrieve
     * @param token          the JWT token
     * @param claimsFunction the function to apply on the claims to retrieve the
     *                       desired claim
     * @return the value of the desired claim
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsFunction) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsFunction.apply(claims);
    }

    /**
     * Retrieves all claims from the provided token.
     *
     * @param token the JWT token
     * @return the claims extracted from the token
     */
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSignatureKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Retrieves the secret key used for generating the signature.
     *
     * @return The secret key used for generating the signature.
     */
    public SecretKey getSignatureKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
