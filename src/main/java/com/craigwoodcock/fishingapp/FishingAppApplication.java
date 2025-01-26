package com.craigwoodcock.fishingapp;

import com.craigwoodcock.fishingapp.model.entity.Role;
import com.craigwoodcock.fishingapp.model.entity.User;
import com.craigwoodcock.fishingapp.repository.UserRepository;
import com.craigwoodcock.fishingapp.utils.DateFormatter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@SpringBootApplication
public class FishingAppApplication {
    private static final Logger log = Logger.getLogger(FishingAppApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(FishingAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {


//                // create/update initial USER account
//                User user = userRepository.findByUsername("craig").orElse(new User());
//                log.info("Updating user account for" + user.getUsername());
//                user.setUsername("craig");
//                user.setName("craig");
//                user.setEmail("craig@craig.com");
//
//                user.setPassword(passwordEncoder.encode("craig"));
//                if (user.getCreatedAt() == null) {
//                    user.setCreatedAt(DateFormatter.formatLocalDateTime(LocalDateTime.now()));
//                }
//                user.setUpdatedAt(DateFormatter.formatLocalDateTime(LocalDateTime.now()));
//                user.setRole(Role.USER);
//                userRepository.save(user);
//                log.info("Demo user account " + user.getUsername() + " has been created/updated with the role " + user.getRole());

                //Create/Update ADMIN account
                User user = userRepository.findByUsername("admin").orElse(new User());
                log.info("Creating/Updating admin account");
                user.setUsername("admin");
                user.setName("admin");
                user.setEmail("admin@admin.com");
                user.setPassword(passwordEncoder.encode("admin"));
                if (user.getCreatedAt() == null) {
                    user.setCreatedAt(DateFormatter.formatLocalDateTime(LocalDateTime.now()));
                }
                user.setUpdatedAt(DateFormatter.formatLocalDateTime(LocalDateTime.now()));
                user.setRole(Role.ADMIN);
                userRepository.save(user);
                log.info("Admin account has been created/updated with the role " + user.getRole());

            }
        };
    }
}
