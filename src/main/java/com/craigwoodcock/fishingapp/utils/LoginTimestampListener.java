package com.craigwoodcock.fishingapp.utils;


import com.craigwoodcock.fishingapp.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Updates a user's last-login timestamp whenever they successfully
 * authenticate. Listening for AuthenticationSuccessEvent (rather than
 * doing this in CustomUserDetailsService) ensures the timestamp only
 * updates on a genuinely successful login — loadUserByUsername runs
 * before the password is checked, so it fires on failed attempts too.
 */
@Component
public class LoginTimestampListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final UserRepository userRepository;

    public LoginTimestampListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        UserDetails principal = (UserDetails) event.getAuthentication().getPrincipal();

        userRepository.findByUsername(principal.getUsername()).ifPresent(user -> {
            user.setLastLoginAt(LocalDateTime.now());
            userRepository.save(user);
        });
    }
}
