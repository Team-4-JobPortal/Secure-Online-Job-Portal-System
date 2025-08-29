package com.example.security;

import com.example.entity.User;
import com.example.service.impl.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;

/* 
 * Custom UserDetailsService implementation for Spring Security authentication.
 * Loads user information from database and converts to Spring Security UserDetails.
 */
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceImpl userServiceImpl;

    /* 
     * Loads user by email/username for authentication purposes.
     * Converts database User entity to Spring Security UserDetails format.
     * @param email The user's email address used as username
     * @return UserDetails object containing user credentials and authorities
     * @throws UsernameNotFoundException if user not found with given email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userServiceImpl.findByemail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // prepend ROLE_ because Spring Security expects it
        String role = "ROLE_" + user.getRole().toUpperCase();

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword()) // must already be BCrypt encoded in DB
                .authorities(new SimpleGrantedAuthority(role))
                .build();
    }
}
