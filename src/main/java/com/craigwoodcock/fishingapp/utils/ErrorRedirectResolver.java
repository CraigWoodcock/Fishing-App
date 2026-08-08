package com.craigwoodcock.fishingapp.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Resolves where an error page's "back" button should point, based
 * on whoever is currently authenticated. Shared by
 * GlobalWebExceptionHandler and CustomErrorController, which reach
 * error views by two different paths but need the same destination
 * logic.
 */
@Component
public class ErrorRedirectResolver {

    public String resolveHomeUrl() {
        if (isAdmin()) {
            return "/admin/dashboard";
        }
        if (isAuthenticated()) {
            return "/dashboard";
        }
        return "/login";
    }
    

    public String resolveHomeLabel() {
        return isAuthenticated() ? "Back to Dashboard" : "Go to Login";
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal());
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}