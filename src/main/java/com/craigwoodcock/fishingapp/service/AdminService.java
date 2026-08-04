package com.craigwoodcock.fishingapp.service;

import com.craigwoodcock.fishingapp.exception.UserAlreadyExistsException;
import com.craigwoodcock.fishingapp.model.dto.AdminSessionView;
import com.craigwoodcock.fishingapp.model.dto.AdminUserView;
import com.craigwoodcock.fishingapp.model.dto.UserDto;
import com.craigwoodcock.fishingapp.model.entity.Role;
import com.craigwoodcock.fishingapp.model.entity.Session;
import com.craigwoodcock.fishingapp.model.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private final UserService userService;
    private final SessionService sessionService;

    public AdminService(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

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

    public void updateUser(Long userId, String name, String username, String email, Role role) {
        userService.adminUpdateUser(userId, name, username, email, role);
    }

    public void resetPassword(Long userId, String newPassword) {
        userService.adminResetPassword(userId, newPassword);
    }

    public void deleteUser(Long userId) {
        userService.deleteUserById(userId);
    }

    /**
     * Registers a new admin account. Throws UserAlreadyExistsException
     * if the username or email is already taken.
     */
    public void registerAdmin(User user) throws UserAlreadyExistsException {
        userService.registerAdminUser(user);
    }

    public List<AdminSessionView> getAllSessionsForAdmin() {
        List<Session> sessions = sessionService.getAllSessions();
        List<AdminSessionView> sessionViews = new ArrayList<>();

        for (Session session : sessions) {
            sessionViews.add(new AdminSessionView(session));
        }

        return sessionViews;
    }

    public Session getSessionForAdmin(Long sessionId) {
        return sessionService.getSessionById(sessionId);
    }

    public void updateSession(Long sessionId, String venue, LocalDate startDate, int durationHours) {
        sessionService.updateSessionDetails(sessionId, venue, startDate, durationHours);
    }

    public void deleteSession(Long sessionId) {
        sessionService.deleteSession(sessionId);
    }
}