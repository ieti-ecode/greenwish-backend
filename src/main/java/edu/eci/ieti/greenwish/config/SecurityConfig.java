package edu.eci.ieti.greenwish.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import edu.eci.ieti.greenwish.models.Role;
import edu.eci.ieti.greenwish.security.JwtRequestFilter;
import lombok.RequiredArgsConstructor;

/**
 * This class is responsible for configuring the security settings of the
 * application.
 * It defines the security filter chain, password encoder, user details service,
 * and authentication manager.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    /**
     * Configures the security filter chain for the application.
     *
     * @param httpSecurity the HttpSecurity object used to configure the security
     *                     filter chain
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs while configuring the security filter
     *                   chain
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(Customizer.withDefaults())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/benefits").hasAnyRole(Role.CUSTOMER.getName(), Role.COMPANY.getName(), Role.ADMINISTRATOR.getName())
                        .requestMatchers(HttpMethod.GET, "/companies").hasAnyRole(Role.CUSTOMER.getName(), Role.COMPANY.getName(), Role.ADMINISTRATOR.getName())
                        .requestMatchers(HttpMethod.GET, "/materials").hasRole(Role.ADMINISTRATOR.getName())
                        .requestMatchers(HttpMethod.GET, "/users").hasAnyRole(Role.CUSTOMER.getName(), Role.COMPANY.getName(), Role.ADMINISTRATOR.getName())
                        .requestMatchers(HttpMethod.POST, "/benefits").hasRole(Role.ADMINISTRATOR.getName())
                        .requestMatchers(HttpMethod.POST, "/companies").hasRole(Role.ADMINISTRATOR.getName())
                        .requestMatchers(HttpMethod.POST, "/materials").hasRole(Role.ADMINISTRATOR.getName())
                        .requestMatchers(HttpMethod.POST, "/users", "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/benefits").hasRole(Role.ADMINISTRATOR.getName())
                        .requestMatchers(HttpMethod.PUT, "/companies").hasRole(Role.ADMINISTRATOR.getName())
                        .requestMatchers(HttpMethod.PUT, "/materials").hasRole(Role.ADMINISTRATOR.getName())
                        .requestMatchers(HttpMethod.PUT, "/users").hasAnyRole(Role.CUSTOMER.getName(), Role.COMPANY.getName(), Role.ADMINISTRATOR.getName())
                        .requestMatchers(HttpMethod.DELETE, "/benefits").hasRole(Role.ADMINISTRATOR.getName())
                        .requestMatchers(HttpMethod.DELETE, "/companies").hasRole(Role.ADMINISTRATOR.getName())
                        .requestMatchers(HttpMethod.DELETE, "/materials").hasRole(Role.ADMINISTRATOR.getName())
                        .requestMatchers(HttpMethod.DELETE, "/users").hasRole(Role.ADMINISTRATOR.getName())
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173");
        configuration.setAllowedMethods(List.of(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name()));
        configuration.setAllowedHeaders(List.of(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCEPT));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Returns a new instance of the PasswordEncoder interface that uses the BCrypt
     * hashing algorithm.
     * This algorithm is commonly used for secure password storage.
     *
     * @return a new instance of the PasswordEncoder interface.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
