package com.craigwoodcock.fishingapp.apiController;

import com.craigwoodcock.fishingapp.exception.UserAlreadyExistsException;
import com.craigwoodcock.fishingapp.model.AuthRequest;
import com.craigwoodcock.fishingapp.model.AuthResponse;
import com.craigwoodcock.fishingapp.model.User;
import com.craigwoodcock.fishingapp.service.UserService;
import com.craigwoodcock.fishingapp.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthApiController(UserService userService, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        User user = userService.findByUsername(request.getUsername());
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtUtils.generateToken(user.getUsername());

            return ResponseEntity.ok(new AuthResponse(token, user.getUsername()));
        }
        return ResponseEntity.badRequest().body(new AuthResponse("Invalid username or password", user.getUsername()));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok(new AuthResponse(jwtUtils.generateToken(user.getUsername()), user.getUsername()));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(new AuthResponse("User already exists", user.getUsername()));
//            throw new UserAlreadyExistsException("User already exists");
        }

    }
}
