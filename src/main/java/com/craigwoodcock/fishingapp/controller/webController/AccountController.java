package com.craigwoodcock.fishingapp.controller.webController;

import com.craigwoodcock.fishingapp.model.entity.User;
import com.craigwoodcock.fishingapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller handling the logged-in user's own account details — viewing
 * and updating name, username, email, and password.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String viewAccount(Authentication authentication, Model model) {
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("user", user);
        return "account";
    }

    /**
     * Applies changes to the logged-in user's account. If the username or
     * password changes, the session is invalidated and the user is sent
     * back to login — their current session's identity is no longer valid
     * once either credential changes.
     */
    @PostMapping("/update")
    public String updateAccount(Authentication authentication,
                                @RequestParam String name,
                                @RequestParam String username,
                                @RequestParam String email,
                                @RequestParam(required = false) String currentPassword,
                                @RequestParam(required = false) String newPassword,
                                @RequestParam(required = false) String confirmNewPassword,
                                HttpServletRequest request,
                                HttpServletResponse response,
                                RedirectAttributes redirectAttributes) {
        User user = userService.findByUsername(authentication.getName());
        boolean credentialsChanged = !username.equalsIgnoreCase(user.getUsername())
                || (newPassword != null && !newPassword.isBlank());

        userService.updateAccountDetails(user.getId(), name, username, email,
                currentPassword, newPassword, confirmNewPassword);

        if (credentialsChanged) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            redirectAttributes.addFlashAttribute("message", "Account updated. Please log in again with your new details.");
            return "redirect:/login";
        }

        redirectAttributes.addFlashAttribute("message", "Account Updated");
        return "redirect:/account";
    }
}