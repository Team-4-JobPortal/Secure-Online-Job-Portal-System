package com.example.security;

import com.example.entity.User;
import com.example.service.impl.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;

import java.util.Collections;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userServiceImpl.findByemail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        // Assuming your User entity stores the hashed password
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(), // hashed password here
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}

