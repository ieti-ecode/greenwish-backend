package edu.eci.ieti.greenwish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import edu.eci.ieti.greenwish.models.dto.UserDto;
import edu.eci.ieti.greenwish.services.UserService;

@SpringBootApplication
public class EcoredApplication {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(EcoredApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            if (!userService.existsAdminUser()) {
                UserDto adminDto = new UserDto();
                adminDto.setName("GreenWish");
                adminDto.setEmail("admin@greenwish.com");
                adminDto.setPassword("adminpassword");
                adminDto.setIsCompany(false);
                userService.save(adminDto);
            }
        };
    }

}
