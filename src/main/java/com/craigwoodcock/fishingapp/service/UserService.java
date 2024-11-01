package com.craigwoodcock.fishingapp.service;

import com.craigwoodcock.fishingapp.exception.UserAlreadyExistsException;
import com.craigwoodcock.fishingapp.model.JwtToken;
import com.craigwoodcock.fishingapp.model.Role;
import com.craigwoodcock.fishingapp.model.User;
import com.craigwoodcock.fishingapp.repository.JwtTokenRepository;
import com.craigwoodcock.fishingapp.repository.UserRepository;
import com.craigwoodcock.fishingapp.utils.DateFormatter;
import com.craigwoodcock.fishingapp.utils.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {
    private final Logger log = Logger.getLogger(this.getClass().getName());
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final JwtTokenRepository jwtTokenRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, JwtTokenRepository jwtTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.jwtTokenRepository = jwtTokenRepository;
    }

    @Transactional
    public void registerUser(User user) throws UserAlreadyExistsException {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        log.info("Registering user: " + user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("encoded password");
        user.setRole(Role.USER);
        log.info("Role Set: " + user.getRole());
        userRepository.save(user);
        log.info("User saved");
        user.setCreatedAt(DateFormatter.formatLocalDateTime(LocalDateTime.now()));
        log.info("Created at: " + user.getCreatedAt());
        user.setUpdatedAt(DateFormatter.formatLocalDateTime(LocalDateTime.now()));
        log.info("Updated at: " + user.getUpdatedAt());
        userRepository.save(user);
        log.info("User saved");
        log.info("Generating JWT token");
        String token = jwtUtils.generateToken(user.getUsername());
        JwtToken jwtToken = new JwtToken();
        log.info("JWT token Created!!");
        jwtToken.setToken(token);
        jwtToken.setUser(user);
        jwtToken.setExpiryDate(LocalDateTime.now().plusDays(10));
        jwtToken.setRevoked(false);
        jwtTokenRepository.save(jwtToken);
        log.info("JWT token saved for user: " + user.getUsername());
    }

    public List<String> getUserTokens(User user) {
        List<JwtToken> tokens = jwtTokenRepository.findByUserAndRevokedFalse(user);
        List<String> tokenStrings = new ArrayList<>();
        for (JwtToken token : tokens) {
            tokenStrings.add(token.getToken());
        }
        return tokenStrings;
    }

    public void revokeToken(String token) {
        JwtToken jwtToken = jwtTokenRepository.findByToken(token);
        if (jwtToken != null) {
            jwtToken.setRevoked(true);
            jwtTokenRepository.save(jwtToken);
        }
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }


    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
