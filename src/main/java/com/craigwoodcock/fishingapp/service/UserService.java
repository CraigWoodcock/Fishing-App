package com.craigwoodcock.fishingapp.service;

import com.craigwoodcock.fishingapp.exception.InvalidCredentialsException;
import com.craigwoodcock.fishingapp.exception.UserAlreadyExistsException;
import com.craigwoodcock.fishingapp.exception.UserForbiddenException;
import com.craigwoodcock.fishingapp.exception.UserNotFoundException;
import com.craigwoodcock.fishingapp.model.dto.UserDto;
import com.craigwoodcock.fishingapp.model.entity.JwtToken;
import com.craigwoodcock.fishingapp.model.entity.Role;
import com.craigwoodcock.fishingapp.model.entity.Session;
import com.craigwoodcock.fishingapp.model.entity.User;
import com.craigwoodcock.fishingapp.repository.JwtTokenRepository;
import com.craigwoodcock.fishingapp.repository.UserRepository;
import com.craigwoodcock.fishingapp.utils.DateFormatter;
import com.craigwoodcock.fishingapp.utils.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class UserService {
    private final Logger log = Logger.getLogger(this.getClass().getName());
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final JwtTokenRepository jwtTokenRepository;
    private final SessionService sessionService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, JwtTokenRepository jwtTokenRepository, SessionService sessionService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.jwtTokenRepository = jwtTokenRepository;
        this.sessionService = sessionService;
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

    }

    @Transactional
    public void registerAdminUser(User user) throws UserAlreadyExistsException {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        log.info("Registering user: " + user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("encoded password");
        user.setRole(Role.ADMIN);
        log.info("Role Set: " + user.getRole());
        userRepository.save(user);
        log.info("User saved");
        user.setCreatedAt(DateFormatter.formatLocalDateTime(LocalDateTime.now()));
        log.info("Created at: " + user.getCreatedAt());
        user.setUpdatedAt(DateFormatter.formatLocalDateTime(LocalDateTime.now()));
        log.info("Updated at: " + user.getUpdatedAt());
        userRepository.save(user);

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

    public UserDto getByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("Username : " + username + "  not found"));
        return new UserDto(user);

    }

    public UserDto getById(Long id) {
        User user = userRepository.getUserById(id).orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found"));
        return new UserDto(user);
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Username not found"));
    }


    public List<User> findAllUsers() throws UserNotFoundException {
        try {
            return userRepository.findAll();
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("No registered users found!");
        }
    }

    public List<UserDto> getAllUsers() throws UserNotFoundException {

        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        try {
            for (User user : users) {
                userDtos.add(new UserDto(user));
            }

            return userDtos;
        } catch (UserNotFoundException ex) {
            throw new UserNotFoundException("No registered users found!");
        }
    }

    public User findById(Long id) throws UserNotFoundException {
        return userRepository.getUserById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

    }

    @Transactional
    public void deleteUserByUsername(String username) throws UserNotFoundException {
        try {
            User user = findByUsername(username);
            deleteUserAndAssociatedData(user);
        } catch (UserNotFoundException ex) {
            throw new UserNotFoundException("No User Found");
        }

    }

    @Transactional
    public void deleteUserById(Long id) {
        User user = findById(id);
        deleteUserAndAssociatedData(user);
    }

    @Transactional
    public void deleteAllUsers(Authentication authentication) {

        String loggedInAdmin = authentication.getName();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            // Only delete users with USER role
            if (!user.getUsername().equals(loggedInAdmin)) {
                deleteUserAndAssociatedData(user);
            }

        }
    }

    @Transactional
    private void deleteUserAndAssociatedData(User user) {
        //revoke jwt tokens for user
        log.info("fetching jwt tokens if not revoked");

        List<JwtToken> userTokens = jwtTokenRepository.findByUserAndRevokedFalse(user);
        long count = userTokens.stream().count();
        log.info("found " + count + " tokens");
        log.info("Deleting tokens for user");
        jwtTokenRepository.deleteAll(userTokens);
        log.info("Tokens deleted");


        // find and delete all associated sessions
        List<Session> userSessions = sessionService.getAllSessionsByUser(user);
        for (Session session : userSessions) {
            sessionService.deleteSession(session.getId());
        }

        userRepository.delete(user);
        log.info("User Deleted: " + user.getUsername());
    }

    /**
     * Authenticate an admin user
     *
     * @throws UserNotFoundException       if user doesn't exist
     * @throws UserForbiddenException      if user is not an admin
     * @throws InvalidCredentialsException if passowrd is wrong
     */

    public User authenticateAdminUser(String username, String password) {
        User user = findByUsername(username);

        // check password matches
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Incorrect Password");
        }
        if (user.getRole() != Role.ADMIN) {
            throw new UserForbiddenException("Admin access is required");
        }
        return user;
    }
}
