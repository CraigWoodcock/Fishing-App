package com.craigwoodcock.fishingapp.model.dto;

import com.craigwoodcock.fishingapp.model.entity.Role;
import com.craigwoodcock.fishingapp.utils.EmailMasker;

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
        this.maskedEmail = EmailMasker.mask(userDto.getEmail());
        this.role = userDto.getRole();
        this.createdAt = userDto.getCreatedAt();
        this.lastLoginAt = userDto.getLastLoginAt();
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