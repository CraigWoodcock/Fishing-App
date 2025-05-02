package com.craigwoodcock.fishingapp.config;

import com.craigwoodcock.fishingapp.model.entity.Role;
import com.craigwoodcock.fishingapp.model.entity.User;
import com.craigwoodcock.fishingapp.repository.UserRepository;
import com.craigwoodcock.fishingapp.utils.DateFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * Initializes default users in the application upon startup.
 * This component is responsible for creating or resetting the admin user
 * based on application properties.
 *
 * @author Craig Woodcock
 * @version 1.0
 */
@Configuration
public class UserInitializer {

    private static final Logger log = Logger.getLogger(UserInitializer.class.getName());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    /**
     * Constructor for UserInitializer.
     *
     * @param userRepository  Repository for user operations
     * @param passwordEncoder Encoder for password hashing
     */
    public UserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Initializes default users when the application starts.
     * This method is triggered by the ApplicationReadyEvent.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initializeUsers() {
        createUserIfNotExists(adminUsername, adminEmail, adminPassword);
    }

    /**
     * Creates a new user or resets an existing user's password.
     *
     * @param username The username for the user
     * @param email    The email address for the user
     * @param password The password for the user
     */
    private void createUserIfNotExists(String username, String email, String password) {
        if (!userRepository.findByUsername(username).isPresent()) {
            User user = new User();
            user.setUsername(username.toLowerCase());
            user.setName(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(Role.ADMIN);
            user.setCreatedAt(DateFormatter.formatLocalDateTime(LocalDateTime.now()));
            user.setUpdatedAt(DateFormatter.formatLocalDateTime(LocalDateTime.now()));
            userRepository.save(user);
            log.info("Created " + Role.ADMIN + " account with username " + username);
        } else {
            log.info("Resetting existing user " + username);
            User user = userRepository.findByUsername(username.toLowerCase()).orElse(null);
            user.setPassword(passwordEncoder.encode(password));
            user.setUpdatedAt(DateFormatter.formatLocalDateTime(LocalDateTime.now()));
            userRepository.save(user);
            log.info(Role.ADMIN + " account with username " + username.toLowerCase() + " has been reset");
        }
    }
}
