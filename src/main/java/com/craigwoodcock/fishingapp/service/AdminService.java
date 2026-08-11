package com.craigwoodcock.fishingapp.service;

import com.craigwoodcock.fishingapp.exception.AnglerLinkedToUserException;
import com.craigwoodcock.fishingapp.exception.UserAlreadyExistsException;
import com.craigwoodcock.fishingapp.model.dto.AdminAnglerView;
import com.craigwoodcock.fishingapp.model.dto.AdminSessionView;
import com.craigwoodcock.fishingapp.model.dto.AdminUserView;
import com.craigwoodcock.fishingapp.model.dto.UserDto;
import com.craigwoodcock.fishingapp.model.entity.Angler;
import com.craigwoodcock.fishingapp.model.entity.Role;
import com.craigwoodcock.fishingapp.model.entity.Session;
import com.craigwoodcock.fishingapp.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Orchestrates every use case behind the admin panel. This is the
 * only class AdminController talks to: it owns the translation
 * between domain objects and admin-facing view DTOs, and applies
 * admin-panel-specific rules (like keeping an untouched email field
 * from overwriting a real address). It never touches a repository
 * directly — entity invariants (uniqueness checks, password
 * encoding) stay inside UserService and SessionService, which are
 * the only classes that own their respective repositories.
 */
@Service
public class AdminService {

    private final UserService userService;
    private final SessionService sessionService;
    private final AnglerService anglerService;

    public AdminService(UserService userService, SessionService sessionService, AnglerService anglerService) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.anglerService = anglerService;
    }

    // ----- Users -----

    /**
     * Builds the list of users shown on the admin users page, with
     * each user's email masked for display.
     */
    public List<AdminUserView> getAllUsersForAdmin() {
        List<UserDto> users = userService.getAllUsers();
        List<AdminUserView> userViews = new ArrayList<>();

        for (UserDto user : users) {
            userViews.add(new AdminUserView(user));
        }

        return userViews;
    }

    /**
     * Returns a single user for display on the edit-user page, with
     * the email masked.
     */
    public AdminUserView getUserForAdmin(Long userId) {
        UserDto user = userService.getById(userId);
        return new AdminUserView(user);
    }

    /**
     * Updates a user's details from the admin panel. The email field
     * on the edit form is deliberately left blank rather than
     * pre-filled with the masked value, so a blank submission here
     * means "leave the email unchanged" rather than "erase it" —
     * this method resolves that by falling back to the existing
     * email whenever the submitted value is blank.
     */
    public void updateUser(Long userId, String name, String username, String email, Role role) {
        String emailToSave = email;

        if (emailToSave == null || emailToSave.isBlank()) {
            UserDto existing = userService.getById(userId);
            emailToSave = existing.getEmail();
        }

        userService.adminUpdateUser(userId, name, username, emailToSave, role);
    }

    /**
     * Resets another user's password. Used only for accounts other
     * than the admin's own — see updateOwnAccount for self-service.
     */
    public void resetPassword(Long userId, String newPassword) {
        userService.adminResetPassword(userId, newPassword);
    }

    public void deleteUser(Long userId) {
        userService.deleteUserById(userId);
    }

    /**
     * Registers a new admin account.
     *
     * @throws UserAlreadyExistsException if the username or email is already taken
     */
    public void registerAdmin(User user) throws UserAlreadyExistsException {
        userService.registerAdminUser(user);
    }

    // ----- Admin's own account -----

    /**
     * Returns the logged-in admin's own account details, unmasked,
     * for display on their account page.
     */
    public UserDto getOwnAccount(String username) {
        return userService.getByUsername(username);
    }

    /**
     * Updates the logged-in admin's own account. Unlike updateUser,
     * this goes through the standard current-password-checked path,
     * since here the admin is changing their own credentials rather
     * than resetting someone else's.
     */
    public void updateOwnAccount(String username, String name, String newUsername, String email,
                                 String currentPassword, String newPassword, String confirmNewPassword) {
        UserDto user = userService.getByUsername(username);
        userService.updateAccountDetails(user.getId(), name, newUsername, email,
                currentPassword, newPassword, confirmNewPassword);
    }

    // ----- Sessions -----

    /**
     * Builds the list of sessions shown on the admin sessions page.
     * Sessions are deliberately view/delete-only from the admin
     * panel — session content belongs to the user who created it,
     * and the only legitimate admin need is moderation/cleanup,
     * which deleteSession already covers.
     */
    public List<AdminSessionView> getAllSessionsForAdmin() {
        List<Session> sessions = sessionService.getAllSessions();
        List<AdminSessionView> sessionViews = new ArrayList<>();

        for (Session session : sessions) {
            sessionViews.add(new AdminSessionView(session));
        }

        return sessionViews;
    }

    public void deleteSession(Long sessionId) {
        sessionService.deleteSession(sessionId);
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
        String linkedUsername = userService.findUsernameByEmail(angler.getEmail());
        return new AdminAnglerView(angler, sessionCount, catchCount, linkedUsername);
    }

    /**
     * Updates an angler's details. Refuses if the angler's email
     * belongs to a registered user — that identity is managed through
     * updateUser instead, since editing it here could silently break
     * the angler-email link that grants that user read access to their
     * own sessions/catches.
     */
    public void updateAngler(Long anglerId, String name, String email) {
        Angler angler = anglerService.getAnglerById(anglerId);
        if (userService.findUsernameByEmail(angler.getEmail()) != null) {
            throw new AnglerLinkedToUserException(
                    "This angler is linked to a registered user and must be managed from the Users page.");
        }

        String emailToSave = email;
        if (emailToSave == null || emailToSave.isBlank()) {
            emailToSave = angler.getEmail();
        }

        anglerService.updateAngler(anglerId, name, emailToSave);
    }

    public void deleteAngler(Long anglerId) {
        Angler angler = anglerService.getAnglerById(anglerId);
        if (userService.findUsernameByEmail(angler.getEmail()) != null) {
            throw new AnglerLinkedToUserException(
                    "This angler is linked to a registered user and cannot be deleted here.");
        }

        anglerService.deleteAngler(anglerId);
    }

    public long getUserCount() {
        return userService.getUserCount();
    }

    public long getSessionCount() {
        return sessionService.getSessionCount();
    }

    public long getAnglerCount() {
        return anglerService.getAnglerCount();
    }
}