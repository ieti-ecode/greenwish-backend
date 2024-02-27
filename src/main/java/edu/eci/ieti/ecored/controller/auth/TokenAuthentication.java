package edu.eci.ieti.ecored.controller.auth;

import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
public class TokenAuthentication extends AbstractAuthenticationToken {

    String token;
    String subject;
    List<String> roles;

    public TokenAuthentication(String token, String subject, List<String> roles) {
        super(null);
        this.token = token;
        this.subject = subject;
        this.roles = roles;
    }

    @Override
    public boolean isAuthenticated() {
        return !token.isEmpty() && !subject.isEmpty() && !roles.isEmpty();
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
