package com.craigwoodcock.fishingapp.controller.webController;

import com.craigwoodcock.fishingapp.exception.UserAlreadyExistsException;
import com.craigwoodcock.fishingapp.model.entity.User;
import com.craigwoodcock.fishingapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller handling user registration operations for the web interface.
 * 
 * @author Craig Woodcock
 * @version 1.0
 */
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Displays the user registration form.
     *
     * @param model The Spring MVC model for view rendering
     * @return The registration page view name
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * Processes the user registration form submission.
     *
     * @param user The user data from the registration form
     * @param bindingResult Validation results
     * @param model The Spring MVC model for view rendering
     * @return The view name to redirect to
     */
    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult);
            return "register";
        }
        try {
            userService.registerUser(user);
            model.addAttribute("message", "User has been registered successfully");
            return "login";
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
