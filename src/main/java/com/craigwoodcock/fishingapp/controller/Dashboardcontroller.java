package com.craigwoodcock.fishingapp.controller;

import com.craigwoodcock.fishingapp.model.Session;
import com.craigwoodcock.fishingapp.model.User;
import com.craigwoodcock.fishingapp.service.SessionService;
import com.craigwoodcock.fishingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class Dashboardcontroller {

    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    public Dashboardcontroller(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping()
    public String getDashboardScreen(Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        List<Session> sessions = sessionService.getAllSessionsByUser(user);
        model.addAttribute("sessions", sessions);
        return "dashboard";
    }
}
