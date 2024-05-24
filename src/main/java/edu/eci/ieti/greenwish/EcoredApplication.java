package edu.eci.ieti.greenwish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.eci.ieti.greenwish.models.domain.Role;
import edu.eci.ieti.greenwish.models.domain.User;
import edu.eci.ieti.greenwish.repositories.UserRepository;
import edu.eci.ieti.greenwish.services.UserService;

@SpringBootApplication
public class EcoredApplication {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(EcoredApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            if (!userService.existsAdminUser()) {
                User admin = new User();
                admin.setName("admin");
                admin.setEmail("greenwish@ecode.com");
                admin.setPasswordHash(passwordEncoder.encode("admin"));
                admin.setRole(Role.ADMINISTRATOR.getName());
                admin.setPoints(10000);
                userRepository.save(admin);
            }
        };
    }

}
