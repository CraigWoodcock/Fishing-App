package com.craigwoodcock.fishingapp.controller.apiController;

import com.craigwoodcock.fishingapp.model.dto.AuthRequest;
import com.craigwoodcock.fishingapp.model.dto.AuthResponse;
import com.craigwoodcock.fishingapp.model.entity.User;
import com.craigwoodcock.fishingapp.service.UserService;
import com.craigwoodcock.fishingapp.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller responsible for handling authentication-related operations
 * such as user login and registration in the Fishing App.
 * <p>
 * This controller provides endpoints for user authentication, utilizing JWT (JSON Web Token)
 * for secure access and Spring Security's PasswordEncoder for password validation.
 *
 * @author Craig Woodcock
 * @version 1.0
 * @since 2024-10-21
 */
@RestController
@RequestMapping("/api/auth")
public class AuthApiController {


    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for AuthApiController that injects required dependencies.
     *
     * @param userService     Manages user-related operations and database interactions
     * @param jwtUtils        Utility for generating and managing JWT tokens
     * @param passwordEncoder Provides password encoding and matching capabilities
     */
    public AuthApiController(UserService userService, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Handles user login requests by validating credentials and generating a JWT token.
     * <p>
     * This endpoint authenticates a user by:
     * 1. Retrieving the user by username
     * 2. Verifying the provided password against the stored encoded password
     * 3. Generating a JWT token upon successful authentication
     *
     * @param request An {@link AuthRequest} containing username and password credentials
     * @return A {@link ResponseEntity} with an {@link AuthResponse} containing:
     * - JWT token on successful login
     * - Username
     * - Error message if authentication fails
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        User user = userService.findByUsername(request.getUsername());
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtUtils.generateToken(user.getUsername());

            return ResponseEntity.ok(new AuthResponse(token, user.getUsername()));
        }
        return ResponseEntity.badRequest().body(new AuthResponse("Invalid username or password", user.getUsername()));
    }

    /**
     * Handles user registration by creating a new user account.
     * <p>
     * This endpoint:
     * 1. Registers a new user via the UserService
     * 2. Generates a JWT token for the newly registered user
     *
     * @param user A {@link User} object containing registration details
     * @return A {@link ResponseEntity} with an {@link AuthResponse} containing:
     * - JWT token for the new user
     * - Username of the registered user
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) {
        userService.registerUser(user);
        // return ResponseEntity.ok(new AuthResponse(jwtUtils.generateToken(user.getUsername()), user.getUsername()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(jwtUtils.generateToken(user.getUsername()), user.getUsername()));


    }
}