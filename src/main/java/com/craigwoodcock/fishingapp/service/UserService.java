package com.craigwoodcock.fishingapp.service;

import com.craigwoodcock.fishingapp.exception.UserAlreadyExistsException;
import com.craigwoodcock.fishingapp.model.Role;
import com.craigwoodcock.fishingapp.model.User;
import com.craigwoodcock.fishingapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public void registerUser(User user) throws UserAlreadyExistsException {
    if(userRepository.findByUsername(user.getUsername()).isPresent()) {
        throw new UserAlreadyExistsException("Username already exists");
    }
    if(userRepository.findByEmail(user.getEmail()).isPresent()) {
        throw new UserAlreadyExistsException("Email already exists");
    }
    log.info("Registering user: " + user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    log.info("encoded password");
        user.setRole(Role.USER);
    log.info("Role Set: " + user.getRole());
        user.setCreatedAt(LocalDateTime.now());
    log.info("Created at: " + user.getCreatedAt());
        userRepository.save(user);
    log.info("User saved");
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }


    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
