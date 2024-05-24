package edu.eci.ieti.greenwish.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * This class is responsible for filtering incoming requests and extracting JWT
 * tokens from the Authorization header.
 * It validates the token and sets the authentication in the security context if
 * the token is valid.
 */
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

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
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null) {
            String[] authElements = authorizationHeader.split(" ");
            if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
                String token = authElements[1];
                try {
                    Authentication authentication = jwtUtils.validateToken(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (ExpiredJwtException e) {
                    SecurityContextHolder.clearContext();
                    jwtUtils.deleteAccessToken(token);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

}
