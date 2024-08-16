package com.craigwoodcock.fishingapp.controller;

import com.craigwoodcock.fishingapp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping("login")
    public String getLoginForm(@ModelAttribute User user,@RequestParam(value = "error", required = false) String error,
    @RequestParam(value = "logout", required = false) String logout, Model model){
        model.addAttribute("user", user);

        if (error != null) {
            model.addAttribute("error", "Invalid username or password.");
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login";
    }
}
