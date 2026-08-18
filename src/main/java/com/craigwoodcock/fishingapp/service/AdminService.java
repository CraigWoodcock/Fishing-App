package com.craigwoodcock.fishingapp.service;

import com.craigwoodcock.fishingapp.exception.AnglerLinkedToUserException;
import com.craigwoodcock.fishingapp.exception.UserAlreadyExistsException;
import com.craigwoodcock.fishingapp.model.dto.AdminAnglerView;
import com.craigwoodcock.fishingapp.model.dto.AdminSessionView;
import com.craigwoodcock.fishingapp.model.dto.AdminUserView;
import com.craigwoodcock.fishingapp.model.dto.UserDto;
import com.craigwoodcock.fishingapp.model.entity.Angler;
import com.craigwoodcock.fishingapp.model.entity.AuditAction;
import com.craigwoodcock.fishingapp.model.entity.AuditLog;
import com.craigwoodcock.fishingapp.model.entity.Role;
import com.craigwoodcock.fishingapp.model.entity.Session;
import com.craigwoodcock.fishingapp.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Orchestrates every use case behind the admin panel. This is the
 * only class AdminController talks to: it owns the translation
 * between domain objects and admin-facing view DTOs, applies
 * admin-panel-specific rules, and records every write to the audit
 * log. It never touches a repository directly — entity invariants
 * stay inside UserService/SessionService/AnglerService, and audit
 * persistence stays inside AuditLogService.
 */
@Service
public class AdminService {

    private final UserService userService;
    private final SessionService sessionService;
    private final AnglerService anglerService;
    private final AuditLogService auditLogService;

    public AdminService(UserService userService, SessionService sessionService,
                        AnglerService anglerService, AuditLogService auditLogService) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.anglerService = anglerService;
        this.auditLogService = auditLogService;
    }

    // ----- Users -----

    public List<AdminUserView> getAllUsersForAdmin() {
        List<UserDto> users = userService.getAllUsers();
        List<AdminUserView> userViews = new ArrayList<>();

        for (UserDto user : users) {
            userViews.add(new AdminUserView(user));
        }

        return userViews;
    }

    public AdminUserView getUserForAdmin(Long userId) {
        UserDto user = userService.getById(userId);
        return new AdminUserView(user);
    }

    public void updateUser(Long userId, String name, String username, String email, Role role, String performedBy) {
        UserDto existing = userService.getById(userId);

        String emailToSave = email;
        if (emailToSave == null || emailToSave.isBlank()) {
            emailToSave = existing.getEmail();
        }

        List<String> changes = describeUserChanges(existing, name, username, emailToSave, role);

        userService.adminUpdateUser(userId, name, username, emailToSave, role);

        if (!changes.isEmpty()) {
            String details = String.join(", ", changes);
            auditLogService.log(performedBy, AuditAction.USER_UPDATED, "User: " + username, details);
        }
    }

    /**
     * Compares a user's current details against the values submitted
     * on the edit form and returns a human-readable description of
     * each field that actually changed. An empty list means the form
     * was submitted with no real changes, in which case the caller
     * should skip writing an audit entry entirely.
     */
    private List<String> describeUserChanges(UserDto existing, String newName, String newUsername,
                                             String newEmail, Role newRole) {
        List<String> changes = new ArrayList<>();

        if (!existing.getName().equals(newName)) {
            changes.add("name updated");
        }
        if (!existing.getUsername().equals(newUsername.toLowerCase())) {
            changes.add("username updated");
        }
        if (!existing.getEmail().equalsIgnoreCase(newEmail)) {
            changes.add("email address updated");
        }
        if (existing.getRole() != newRole) {
            changes.add("role updated");
        }

        return changes;
    }

    public void resetPassword(Long userId, String newPassword, String performedBy) {
        UserDto user = userService.getById(userId);
        userService.adminResetPassword(userId, newPassword);
        auditLogService.log(performedBy, AuditAction.USER_PASSWORD_RESET, "User: " + user.getUsername());
    }

    public void deleteUser(Long userId, String performedBy) {
        UserDto user = userService.getById(userId);
        userService.deleteUserById(userId);
        auditLogService.log(performedBy, AuditAction.USER_DELETED, "User: " + user.getUsername());
    }

    public void registerAdmin(User user, String performedBy) throws UserAlreadyExistsException {
        userService.registerAdminUser(user);
        auditLogService.log(performedBy, AuditAction.ADMIN_REGISTERED, "New admin: " + user.getUsername());
    }

    public long getUserCount() {
        return userService.getUserCount();
    }

    // ----- Admin's own account -----

    public UserDto getOwnAccount(String username) {
        return userService.getByUsername(username);
    }

    public void updateOwnAccount(String username, String name, String newUsername, String email,
                                 String currentPassword, String newPassword, String confirmNewPassword) {
        UserDto user = userService.getByUsername(username);
        userService.updateAccountDetails(user.getId(), name, newUsername, email,
                currentPassword, newPassword, confirmNewPassword);
        auditLogService.log(username, AuditAction.ACCOUNT_SELF_UPDATED, "Own account");
    }

    // ----- Sessions (view/delete only, no edit) -----

    public List<AdminSessionView> getAllSessionsForAdmin() {
        List<Session> sessions = sessionService.getAllSessions();
        List<AdminSessionView> sessionViews = new ArrayList<>();

        for (Session session : sessions) {
            sessionViews.add(new AdminSessionView(session));
        }

        return sessionViews;
    }

    public void deleteSession(Long sessionId, String performedBy) {
        Session session = sessionService.getSessionById(sessionId);
        String description = "Session at " + session.getVenue() + " (owner: " + session.getUser().getUsername() + ")";

        sessionService.deleteSession(sessionId);
        auditLogService.log(performedBy, AuditAction.SESSION_DELETED, description);
    }

    public long getSessionCount() {
        return sessionService.getSessionCount();
    }

    // ----- Anglers -----

    public List<AdminAnglerView> getAllAnglersForAdmin() {
        List<Angler> anglers = anglerService.getAllAnglers();
        List<AdminAnglerView> anglerViews = new ArrayList<>();

        for (Angler angler : anglers) {
            anglerViews.add(buildAnglerView(angler));
        }

        return anglerViews;
    }

    public AdminAnglerView getAnglerForAdmin(Long anglerId) {
        Angler angler = anglerService.getAnglerById(anglerId);
        return buildAnglerView(angler);
    }

    private AdminAnglerView buildAnglerView(Angler angler) {
        int sessionCount = anglerService.getSessionCountForAngler(angler.getId());
        int catchCount = anglerService.getCatchCountForAngler(angler.getId());

        Optional<UserDto> linkedUser = userService.findByEmail(angler.getEmail());
        Long linkedUserId = linkedUser.map(UserDto::getId).orElse(null);
        String linkedUsername = linkedUser.map(UserDto::getUsername).orElse(null);

        return new AdminAnglerView(angler, sessionCount, catchCount, linkedUserId, linkedUsername);
    }

    public void updateAngler(Long anglerId, String name, String email, String performedBy) {
        Angler angler = anglerService.getAnglerById(anglerId);
        if (userService.findByEmail(angler.getEmail()).isPresent()) {
            throw new AnglerLinkedToUserException(
                    "This angler is linked to a registered user and must be managed from the Users page.");
        }

        String emailToSave = email;
        if (emailToSave == null || emailToSave.isBlank()) {
            emailToSave = angler.getEmail();
        }

        List<String> changes = describeAnglerChanges(angler, name, emailToSave);

        anglerService.updateAngler(anglerId, name, emailToSave);

        if (!changes.isEmpty()) {
            String details = String.join(", ", changes);
            auditLogService.log(performedBy, AuditAction.ANGLER_UPDATED, "Angler: " + name, details);
        }
    }

    private List<String> describeAnglerChanges(Angler existing, String newName, String newEmail) {
        List<String> changes = new ArrayList<>();

        if (!existing.getName().equals(newName)) {
            changes.add("name updated");
        }
        if (!existing.getEmail().equalsIgnoreCase(newEmail)) {
            changes.add("email address updated");
        }

        return changes;
    }

    public void deleteAngler(Long anglerId, String performedBy) {
        Angler angler = anglerService.getAnglerById(anglerId);
        if (userService.findByEmail(angler.getEmail()).isPresent()) {
            throw new AnglerLinkedToUserException(
                    "This angler is linked to a registered user and cannot be deleted here.");
        }

        anglerService.deleteAngler(anglerId);
        auditLogService.log(performedBy, AuditAction.ANGLER_DELETED, "Angler: " + angler.getName());
    }

    public long getAnglerCount() {
        return anglerService.getAnglerCount();
    }

    // ----- Audit log -----

    public List<AuditLog> getRecentAuditLogs() {
        return auditLogService.getRecentLogs();
    }
}