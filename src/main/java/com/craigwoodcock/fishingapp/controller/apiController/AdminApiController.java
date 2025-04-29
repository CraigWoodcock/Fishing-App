package com.craigwoodcock.fishingapp.controller.apiController;

import com.craigwoodcock.fishingapp.exception.UserAlreadyExistsException;
import com.craigwoodcock.fishingapp.model.dto.AuthRequest;
import com.craigwoodcock.fishingapp.model.dto.AuthResponse;
import com.craigwoodcock.fishingapp.model.dto.UserDto;
import com.craigwoodcock.fishingapp.model.entity.Role;
import com.craigwoodcock.fishingapp.model.entity.User;
import com.craigwoodcock.fishingapp.service.UserService;
import com.craigwoodcock.fishingapp.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminApiController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AdminApiController(UserService userService, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Endpoint to register a new admin user.
     * Only accessible by users with ADMIN role.
     *
     * @param user The user object containing admin registration details
     * @return ResponseEntity with the created UserDto or error message
     */
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerAdminUser(@RequestBody User user) {
        try {
            userService.registerAdminUser(user);
            UserDto createdUser = userService.getByUsername(user.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error registering admin user: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> adminLogin(@RequestBody AuthRequest request) {
        User user = userService.findByUsername(request.getUsername());
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // Explicitly check for ADMIN role
            if (user.getRole() == Role.ADMIN) {
                String token = jwtUtils.generateToken(user.getUsername());
                return ResponseEntity.ok(new AuthResponse(token, user.getUsername()));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new AuthResponse("Admin access denied", user.getUsername()));
            }
        }
        return ResponseEntity.badRequest()
                .body(new AuthResponse("Invalid username or password", null));
    }

    /**
     * Endpoint to delete a user by username.
     * Only accessible by users with ADMIN role.
     *
     * @param username The username of the user to be deleted
     * @return ResponseEntity with a success message
     */

    @DeleteMapping("/users/username/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) {
        userService.deleteUserByUsername(username);
        return ResponseEntity.ok("User deleted " + username);
    }

    /**
     * Endpoint to delete a user by user ID.
     * Only accessible by users with ADMIN role.
     *
     * @param userId The ID of the user to be deleted
     * @return ResponseEntity with a success message
     */
    @DeleteMapping("/users/id/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User with ID " + userId + " deleted successfully");
    }


    /**
     * Endpoint to delete all users with USER role.
     * Only accessible by users with ADMIN role.
     *
     * @return ResponseEntity with a success message
     */
    @DeleteMapping("/users/delete/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAllUsers(Authentication authentication) {
        userService.deleteAllUsers(authentication);
        return ResponseEntity.ok("All users deleted successfully");
    }


    /**
     * Endpoint to retrieve all users.
     * Only accessible by users with ADMIN role.
     *
     * @return ResponseEntity with a list of UserDto objects
     */
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}

