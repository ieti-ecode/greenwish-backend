package edu.eci.ieti.greenwish.controller.auth;

import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Builder
public class TokenAuthentication extends AbstractAuthenticationToken {

    private String token;
    private String subject;
    private List<String> roles;

    public TokenAuthentication(String token, String subject, List<String> roles) {
        super(null);
        this.token = token;
        this.subject = subject;
        this.roles = roles;
    }

    @Override
    public boolean isAuthenticated() {
        return !token.isEmpty() && !subject.isEmpty();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

}
