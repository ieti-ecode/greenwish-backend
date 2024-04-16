package edu.eci.ieti.greenwish.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import edu.eci.ieti.greenwish.security.filters.JwtRequestFilter;

/**
 * This class is responsible for configuring the security settings of the
 * application.
 * It defines the security filter chain, password encoder, user details service,
 * and authentication manager.
 */
@Configuration
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    /**
     * This class represents the configuration for security in the application.
     * It provides methods to configure the JWT request filter.
     */
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

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
                .addFilterBefore(jwtRequestFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/auth").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
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

    /**
     * Returns an instance of UserDetailsService.
     * This method creates an instance of InMemoryUserDetailsManager and adds a user
     * with the username "greenwish",
     * password "1234", and role "ADMIN".
     * 
     * @return an instance of UserDetailsService
     */
    @Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("greenwish")
                .password("1234")
                .roles("ADMIN")
                .build());
        return manager;
    }

    /**
     * Creates and returns an instance of the AuthenticationManager.
     *
     * @param httpSecurity    the HttpSecurity object used for configuring security
     *                        settings
     * @param passwordEncoder the PasswordEncoder object used for encoding passwords
     * @return the created AuthenticationManager instance
     * @throws Exception if an error occurs while creating the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder)
            throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }
}
