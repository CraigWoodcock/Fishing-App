package com.craigwoodcock.fishingapp.controller.webController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
    @GetMapping("/403")
    public String accessDenied(Model model) {
        model.addAttribute("error", "Access Denied - You don't have permission to access this page.");
        model.addAttribute("message", "Admins must log in using the Admin only login endpoint");
        return "error/403";
    }
}
