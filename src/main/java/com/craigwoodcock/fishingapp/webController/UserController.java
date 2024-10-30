package com.craigwoodcock.fishingapp.webController;

import com.craigwoodcock.fishingapp.exception.UserAlreadyExistsException;
import com.craigwoodcock.fishingapp.model.User;
import com.craigwoodcock.fishingapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "/register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult);
            return "register";
        }
        try {
            userService.registerUser(user);
            model.addAttribute("message", "User has been registered successfully");
            return "/login";
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
