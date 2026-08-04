package com.craigwoodcock.fishingapp.model.dto;

import com.craigwoodcock.fishingapp.model.entity.Role;

import java.time.LocalDateTime;

public class AdminUserView {
    private final Long id;
    private final String username;
    private final String name;
    private final String maskedEmail;
    private final Role role;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastLoginAt;

    public AdminUserView(UserDto userDto) {
        this.id = userDto.getId();
        this.username = userDto.getUsername();
        this.name = userDto.getName();
        this.maskedEmail = maskEmail(userDto.getEmail());
        this.role = userDto.getRole();
        this.createdAt = userDto.getCreatedAt();
        this.lastLoginAt = userDto.getLastLoginAt();
    }

    private static String maskEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) {
            return "***" + email.substring(atIndex);
        }
        String localPart = email.substring(0, atIndex);
        String domainPart = email.substring(atIndex);
        String visiblePrefix = localPart.substring(0, Math.min(2, localPart.length()));
        return visiblePrefix + "***" + domainPart;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getMaskedEmail() {
        return maskedEmail;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }
}