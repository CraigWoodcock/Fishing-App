package com.craigwoodcock.fishingapp.controller.apiController;

import com.craigwoodcock.fishingapp.model.dto.UserDto;
import com.craigwoodcock.fishingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

/**
 * REST API controller for user management operations.
 * 
 * @author Craig Woodcock
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
public class UserApiController {

    @Autowired
    private UserService userService;

    /**
     * Retrieves all users.
     *
     * @return List of user DTOs
     */
    @GetMapping("/users")
    @Operation(summary = "Get all users", description = "Retrieves a list of all registered users")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved users list")
    public List<UserDto> getUsers() {
        return userService.getAllUsers();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The user ID
     * @return UserDto containing user information
     */
    @GetMapping("/userid/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique identifier")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user")
    @ApiResponse(responseCode = "404", description = "User not found")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping("username/{username}")
    public UserDto getUserByUsername(@PathVariable String username) {
        return userService.getByUsername(username);
    }

}
