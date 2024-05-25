package edu.eci.ieti.greenwish.security;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import edu.eci.ieti.greenwish.models.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;

/**
 * Utility class for handling JSON Web Tokens (JWT).
 * This class provides methods for generating and parsing JWTs, as well as
 * retrieving claims from JWTs.
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtils {

    private String secret;
    private String expiration;
    private Map<String, User> listToken = new HashMap<>();

    /**
     * Generates an access token for the given user with the specified expiration
     * date.
     *
     * @param user The user for whom the access token is generated.
     * @return The generated access token as a string.
     */
    public String generateAccessToken(User user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + Long.parseLong(expiration));
        SecretKey key = getSignatureKey();
        String tokenCreated = Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
        listToken.put(tokenCreated, user);
        return tokenCreated;
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

    /**
     * Deletes the access token from the list of tokens.
     *
     * @param jwt The access token to be deleted.
     * @return A message indicating the result of the deletion.
     */
    public String deleteAccessToken(String jwt) {
        if (!listToken.containsKey(jwt))
            return "Token not found";
        listToken.remove(jwt);
        return "Removed session";
    }

    /**
     * Represents an authentication object that contains information about an
     * authenticated user.
     * This class is used to encapsulate the user's details and authorities after
     * successful authentication.
     */
    public Authentication validateToken(String token) {
        getAllClaimsFromToken(token);
        User user = listToken.get(token);
        Set<SimpleGrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return new UsernamePasswordAuthenticationToken(user, token, roles);
    }
}
