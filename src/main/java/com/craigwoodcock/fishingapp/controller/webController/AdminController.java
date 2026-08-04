package com.craigwoodcock.fishingapp.controller.webController;

import com.craigwoodcock.fishingapp.exception.UserAlreadyExistsException;
import com.craigwoodcock.fishingapp.model.entity.Role;
import com.craigwoodcock.fishingapp.model.entity.User;
import com.craigwoodcock.fishingapp.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/login")
    public String adminLoginForm() {
        return "admin/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("userCount", adminService.getAllUsersForAdmin().size());
        model.addAttribute("sessionCount", adminService.getAllSessionsForAdmin().size());
        return "admin/dashboard";
    }

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
                             @RequestParam String email,
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

    @GetMapping("/sessions")
    public String listSessions(Model model) {
        model.addAttribute("sessions", adminService.getAllSessionsForAdmin());
        return "admin/sessions/list";
    }

    @GetMapping("/sessions/{id}/edit")
    public String editSessionForm(@PathVariable Long id, Model model) {
        model.addAttribute("session", adminService.getSessionForAdmin(id));
        return "admin/sessions/edit";
    }

    @PostMapping("/sessions/{id}")
    public String updateSession(@PathVariable Long id,
                                @RequestParam String venue,
                                @RequestParam LocalDate startDate,
                                @RequestParam int durationHours,
                                RedirectAttributes redirectAttributes) {
        adminService.updateSession(id, venue, startDate, durationHours);
        redirectAttributes.addFlashAttribute("success", "Session updated");
        return "redirect:/admin/sessions";
    }

    @PostMapping("/sessions/{id}/delete")
    public String deleteSession(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        adminService.deleteSession(id);
        redirectAttributes.addFlashAttribute("success", "Session deleted");
        return "redirect:/admin/sessions";
    }
}