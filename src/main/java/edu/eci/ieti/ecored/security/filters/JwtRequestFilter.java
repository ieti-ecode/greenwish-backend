package edu.eci.ieti.ecored.security.filters;

import edu.eci.ieti.ecored.controller.auth.TokenAuthentication;
import edu.eci.ieti.ecored.security.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Autowired
    public JwtRequestFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                // Get token from header
                String token = authorizationHeader.substring(7);
                // Validate token and get claims
                String userName = jwtUtils.getSubjectFromToken(token);
                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    List<String> roles = jwtUtils.getRolesFromToken(token);
                    TokenAuthentication tokenAuthentication = new TokenAuthentication(token, userName, roles);
                    SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
                }
            }
        } catch (ExpiredJwtException e) {
            logger.error("Error: ", e);
        }
        filterChain.doFilter(request, response);
    }
}

