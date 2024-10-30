package com.craigwoodcock.fishingapp.webController;

import com.craigwoodcock.fishingapp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @GetMapping("/login")
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

    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message", "You have been logged out successfully.");
        return "redirect:/login";
    }
}
