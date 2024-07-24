package com.craigwoodcock.fishingapp.service;

import com.craigwoodcock.fishingapp.model.User;
import com.craigwoodcock.fishingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

   @Autowired
    private UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"+username));

       return new org.springframework.security.core.userdetails.User(
               user.getUsername(),
               user.getPassword(),
               user.getAuthorities()
       );
    }
}
