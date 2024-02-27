package edu.eci.ieti.ecored.security.jwt;

import edu.eci.ieti.ecored.controller.auth.TokenDto;
import edu.eci.ieti.ecored.repository.document.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
@Slf4j
@Getter
@ConfigurationProperties(prefix = "jwt")
public class JwtUtils {

    private String secret;
    private String expiration;

    // Generate TokenDto
    public TokenDto generateTokenDto(User user) {
        Date expirationDate = new Date(System.currentTimeMillis() + Long.parseLong(expiration));
        return new TokenDto(generateAccessToken(user, expirationDate), expirationDate);
    }

    // Generate access token
    public String generateAccessToken(User user, Date expirationDate) {
        return Jwts.builder()
                .subject(user.getId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expirationDate)
                .signWith(getSignatureKey())
                .compact();
    }

    // Obtain subject from token
    public String getSubjectFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Obtain roles from token
    public List<String> getRolesFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("roles", List.class));
    }

    // Obtain only one claim from the token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsFunction) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsFunction.apply(claims);
    }

    // Obtain all claims from the token if it is valid
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSignatureKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Get signature key
    public SecretKey getSignatureKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}