package com.craigwoodcock.fishingapp.controller.webController;

import com.craigwoodcock.fishingapp.exception.AnglerAlreadyExistsException;
import com.craigwoodcock.fishingapp.exception.AnglerHasRecordsException;
import com.craigwoodcock.fishingapp.exception.InvalidCredentialsException;
import com.craigwoodcock.fishingapp.exception.UserAlreadyExistsException;
import com.craigwoodcock.fishingapp.model.entity.Role;
import com.craigwoodcock.fishingapp.model.entity.User;
import com.craigwoodcock.fishingapp.service.AdminService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Handles HTTP routing for the Thymeleaf-based admin panel. This
 * class contains no business logic of its own — every request is
 * delegated straight to AdminService, and this controller's job is
 * limited to reading request parameters, choosing a view, and
 * carrying flash messages between redirects. All routes under
 * /admin are restricted to ROLE_ADMIN by SecurityConfig.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ----- Login / dashboard -----

    @GetMapping("/login")
    public String loginForm() {
        return "admin/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("userCount", adminService.getAllUsersForAdmin().size());
        model.addAttribute("sessionCount", adminService.getAllSessionsForAdmin().size());
        model.addAttribute("AnglerCount", adminService.getAllAnglersForAdmin());
        return "admin/dashboard";
    }

    // ----- Admin's own account -----

    @GetMapping("/account")
    public String viewOwnAccount(Model model, Authentication authentication) {
        model.addAttribute("user", adminService.getOwnAccount(authentication.getName()));
        return "admin/account";
    }

    @PostMapping("/account")
    public String updateOwnAccount(@RequestParam String name,
                                   @RequestParam String username,
                                   @RequestParam String email,
                                   @RequestParam(required = false) String currentPassword,
                                   @RequestParam(required = false) String newPassword,
                                   @RequestParam(required = false) String confirmNewPassword,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes) {
        try {
            adminService.updateOwnAccount(authentication.getName(), name, username, email,
                    currentPassword, newPassword, confirmNewPassword);
            redirectAttributes.addFlashAttribute("success", "Account updated");
        } catch (UserAlreadyExistsException | InvalidCredentialsException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/account";
    }

    // ----- Users -----

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", adminService.getAllUsersForAdmin());
        return "admin/users/list";
    }

    @GetMapping("/users/{id}/edit")
    public String editUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", adminService.getUserForAdmin(id));
        return "admin/users/edit";
    }

    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable Long id,
                             @RequestParam String name,
                             @RequestParam String username,
                             @RequestParam(required = false) String email,
                             @RequestParam Role role,
                             RedirectAttributes redirectAttributes) {
        try {
            adminService.updateUser(id, name, username, email, role);
            redirectAttributes.addFlashAttribute("success", "User updated");
        } catch (UserAlreadyExistsException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/password")
    public String resetPassword(@PathVariable Long id,
                                @RequestParam String newPassword,
                                RedirectAttributes redirectAttributes) {
        adminService.resetPassword(id, newPassword);
        redirectAttributes.addFlashAttribute("success", "Password reset");
        return "redirect:/admin/users/" + id + "/edit";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        adminService.deleteUser(id);
        redirectAttributes.addFlashAttribute("success", "User deleted");
        return "redirect:/admin/users";
    }

    @GetMapping("/register")
    public String registerAdminForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/register";
    }

    @PostMapping("/register")
    public String registerAdmin(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            adminService.registerAdmin(user);
            redirectAttributes.addFlashAttribute("success", "Admin user registered");
        } catch (UserAlreadyExistsException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/admin/register";
        }
        return "redirect:/admin/users";
    }

    // ----- Sessions (view/delete only, no edit) -----

    @GetMapping("/sessions")
    public String listSessions(Model model) {
        model.addAttribute("sessions", adminService.getAllSessionsForAdmin());
        return "admin/sessions/list";
    }

    @PostMapping("/sessions/{id}/delete")
    public String deleteSession(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        adminService.deleteSession(id);
        redirectAttributes.addFlashAttribute("success", "Session deleted");
        return "redirect:/admin/sessions";
    }

    // ----- Anglers -----
    @GetMapping("/anglers")
    public String listAnglers(Model model) {
        model.addAttribute("anglers", adminService.getAllAnglersForAdmin());
        return "admin/anglers/list";
    }

    @GetMapping("/anglers/{id}/edit")
    public String editAnglerForm(@PathVariable Long id, Model model) {
        model.addAttribute("angler", adminService.getAnglerForAdmin(id));
        return "admin/anglers/edit";
    }

    @PostMapping("/anglers/{id}")
    public String updateAngler(@PathVariable Long id,
                               @RequestParam String name,
                               @RequestParam(required = false) String email,
                               RedirectAttributes redirectAttributes) {
        try {
            adminService.updateAngler(id, name, email);
            redirectAttributes.addFlashAttribute("success", "Angler updated");
        } catch (AnglerAlreadyExistsException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/anglers";
    }

    @PostMapping("/anglers/{id}/delete")
    public String deleteAngler(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteAngler(id);
            redirectAttributes.addFlashAttribute("success", "Angler deleted");
        } catch (AnglerHasRecordsException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/anglers";
    }
}