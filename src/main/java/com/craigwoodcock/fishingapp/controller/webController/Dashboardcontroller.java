package com.craigwoodcock.fishingapp.controller.webController;

import com.craigwoodcock.fishingapp.model.entity.Session;
import com.craigwoodcock.fishingapp.model.entity.User;
import com.craigwoodcock.fishingapp.service.SessionService;
import com.craigwoodcock.fishingapp.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller for the user's dashboard, showing both the sessions they own
 * and any sessions they've been added to as an angler and can therefore
 * view but not edit.
 *
 * @author Craig Woodcock
 * @version 1.0
 */
@Controller
@RequestMapping("/dashboard")
public class Dashboardcontroller {

    private final UserService userService;
    private final SessionService sessionService;

    public Dashboardcontroller(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping()
    public String getDashboardScreen(Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        List<Session> sessions = sessionService.getAllSessionsByUser(user);
        List<Session> sharedSessions = sessionService.getSessionsSharedWithUser(user);

        model.addAttribute("sessions", sessions);
        model.addAttribute("sharedSessions", sharedSessions);
        return "dashboard";
    }
}