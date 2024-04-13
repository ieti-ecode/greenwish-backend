package edu.eci.ieti.greenwish.security.filters;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.eci.ieti.greenwish.controller.auth.TokenAuthentication;
import edu.eci.ieti.greenwish.security.jwt.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This class is responsible for filtering incoming requests and extracting JWT
 * tokens from the Authorization header.
 * It validates the token and sets the authentication in the security context if
 * the token is valid.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    /**
     * This class is responsible for filtering incoming requests and validating JWT
     * tokens.
     * It implements the logic to extract the JWT token from the request header and
     * validate it using JwtUtils.
     */
    public JwtRequestFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * Filters the incoming request and response to validate the JWT token in the
     * authorization header.
     * If the token is valid, sets the authentication in the security context.
     *
     * @param request     the incoming HTTP servlet request
     * @param response    the HTTP servlet response
     * @param filterChain the filter chain for invoking the next filter or the
     *                    servlet
     * @throws ServletException if there is a servlet-related problem
     * @throws IOException      if there is an I/O problem
     */
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
                    TokenAuthentication tokenAuthentication = TokenAuthentication.builder()
                            .token(token).subject(userName).build();
                    SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
                }
            }
        } catch (ExpiredJwtException e) {
            logger.error("Error: ", e);
        }
        filterChain.doFilter(request, response);
    }

}
