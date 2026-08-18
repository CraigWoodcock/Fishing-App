package com.craigwoodcock.fishingapp.controller.webController;

import com.craigwoodcock.fishingapp.exception.AnglerAlreadyExistsException;
import com.craigwoodcock.fishingapp.exception.AnglerHasRecordsException;
import com.craigwoodcock.fishingapp.exception.AnglerLinkedToUserException;
import com.craigwoodcock.fishingapp.exception.InvalidCredentialsException;
import com.craigwoodcock.fishingapp.exception.UserAlreadyExistsException;
import com.craigwoodcock.fishingapp.model.entity.Role;
import com.craigwoodcock.fishingapp.model.entity.User;
import com.craigwoodcock.fishingapp.service.AdminService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("userCount", adminService.getUserCount());
        model.addAttribute("sessionCount", adminService.getSessionCount());
        model.addAttribute("anglerCount", adminService.getAnglerCount());
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
                             Authentication authentication,
                             RedirectAttributes redirectAttributes) {
        try {
            adminService.updateUser(id, name, username, email, role, authentication.getName());
            redirectAttributes.addFlashAttribute("success", "User updated");
        } catch (UserAlreadyExistsException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/password")
    public String resetPassword(@PathVariable Long id,
                                @RequestParam String newPassword,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes) {
        adminService.resetPassword(id, newPassword, authentication.getName());
        redirectAttributes.addFlashAttribute("success", "Password reset");
        return "redirect:/admin/users/" + id + "/edit";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes) {
        adminService.deleteUser(id, authentication.getName());
        redirectAttributes.addFlashAttribute("success", "User deleted");
        return "redirect:/admin/users";
    }

    @GetMapping("/register")
    public String registerAdminForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/register";
    }

    @PostMapping("/register")
    public String registerAdmin(@ModelAttribute User user,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes) {
        try {
            adminService.registerAdmin(user, authentication.getName());
            redirectAttributes.addFlashAttribute("success", "Admin user registered");
        } catch (UserAlreadyExistsException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/admin/register";
        }
        return "redirect:/admin/dashboard";
    }

    // ----- Sessions (view/delete only, no edit) -----

    @GetMapping("/sessions")
    public String listSessions(Model model) {
        model.addAttribute("sessions", adminService.getAllSessionsForAdmin());
        return "admin/sessions/list";
    }

    @PostMapping("/sessions/{id}/delete")
    public String deleteSession(@PathVariable Long id,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes) {
        adminService.deleteSession(id, authentication.getName());
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
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        try {
            adminService.updateAngler(id, name, email, authentication.getName());
            redirectAttributes.addFlashAttribute("success", "Angler updated");
        } catch (AnglerAlreadyExistsException | AnglerLinkedToUserException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/anglers";
    }

    @PostMapping("/anglers/{id}/delete")
    public String deleteAngler(@PathVariable Long id,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteAngler(id, authentication.getName());
            redirectAttributes.addFlashAttribute("success", "Angler deleted");
        } catch (AnglerHasRecordsException | AnglerLinkedToUserException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/anglers";
    }

    // ----- Audit log -----

    @GetMapping("/audit-log")
    public String viewAuditLog(Model model) {
        model.addAttribute("logs", adminService.getRecentAuditLogs());
        return "admin/audit-log";
    }
}