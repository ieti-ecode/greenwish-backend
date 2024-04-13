package edu.eci.ieti.greenwish.controller.auth;

import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import lombok.Builder;
import lombok.EqualsAndHashCode;

/**
 * Represents a token-based authentication object.
 */
@EqualsAndHashCode(callSuper = false)
@Builder
public class TokenAuthentication extends AbstractAuthenticationToken {

    private String token;
    private String subject;
    private List<String> roles;

    /**
     * Constructs a new TokenAuthentication object with the specified token, subject, and roles.
     *
     * @param token   The authentication token.
     * @param subject The subject of the authentication.
     * @param roles   The roles associated with the authentication.
     */
    public TokenAuthentication(String token, String subject, List<String> roles) {
        super(null);
        this.token = token;
        this.subject = subject;
        this.roles = roles;
    }

    /**
     * Checks if the authentication is valid.
     *
     * @return True if the authentication is valid, false otherwise.
     */
    @Override
    public boolean isAuthenticated() {
        return !token.isEmpty() && !subject.isEmpty();
    }

    /**
     * Gets the credentials associated with the authentication.
     *
     * @return The credentials associated with the authentication.
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * Gets the principal associated with the authentication.
     *
     * @return The principal associated with the authentication.
     */
    @Override
    public Object getPrincipal() {
        return null;
    }

}
