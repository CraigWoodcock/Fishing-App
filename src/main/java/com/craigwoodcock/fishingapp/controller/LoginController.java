package com.craigwoodcock.fishingapp.controller;

import com.craigwoodcock.fishingapp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String getLoginForm(@ModelAttribute User user, Model model){
        model.addAttribute("user", user);
        return "login";
    }
}
