package com.craigwoodcock.fishingapp.config;

import com.craigwoodcock.fishingapp.model.entity.Role;
import com.craigwoodcock.fishingapp.model.entity.User;
import com.craigwoodcock.fishingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Initializes the default admin user on application startup.
 * Creates the admin account if it does not exist. If it already exists,
 * the password is only reset when admin.reset-password-on-startup is true.
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

    @Value("${admin.reset-password-on-startup:false}")
    private boolean resetPasswordOnStartup;

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
     * Initializes the default admin user when the application starts.
     * This method is triggered by the ApplicationReadyEvent.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initializeUsers() {
        createOrResetAdmin(adminUsername, adminEmail, adminPassword);
    }

    /**
     * Creates the admin user if it does not already exist. If it does exist,
     * the password is only reset when admin.reset-password-on-startup is true.
     *
     * @param username The username for the admin user
     * @param email    The email address for the admin user
     * @param password The password for the admin user
     */
    private void createOrResetAdmin(String username, String email, String password) {
        Optional<User> existingUser = userRepository.findByUsername(username.toLowerCase());

        if (existingUser.isEmpty()) {
            User user = new User();
            user.setUsername(username.toLowerCase());
            user.setName(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(Role.ADMIN);
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);
            log.info("Created " + Role.ADMIN + " account with username " + username.toLowerCase());
        } else if (resetPasswordOnStartup) {
            User user = existingUser.get();
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            log.info(Role.ADMIN + " account with username " + username.toLowerCase() + " has been reset");
        } else {
            log.info(Role.ADMIN + " Account found with username: " + username.toLowerCase() + " Starting Application ");
        }
    }
}