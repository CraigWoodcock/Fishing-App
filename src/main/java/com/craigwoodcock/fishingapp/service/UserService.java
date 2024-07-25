package com.craigwoodcock.fishingapp.service;

import com.craigwoodcock.fishingapp.model.Role;
import com.craigwoodcock.fishingapp.model.User;
import com.craigwoodcock.fishingapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserService {
private final Logger log = Logger.getLogger(this.getClass().getName());
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
@Transactional
    public void registerUser(User user) {
    try {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);
        log.info("User saved successfully: " + savedUser.getId());
    } catch (Exception e) {
        log.severe("Error saving user" +e.getMessage());

        throw e; // Re-throw to roll back transaction
    }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }


    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
