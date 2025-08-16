package com.example.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;




public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {
    	
    	  String path = request.getRequestURI();
    	  
    	 if (path.startsWith("/auth/") || path.startsWith("/register") || path.startsWith("/users/")) {
    	        filterChain.doFilter(request, response);
    	        return;
    	    }

    	    System.out.println("Hello Filter");
        String header = request.getHeader("Authorization");
        
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            String username = jwtUtil.getUsername(token);
            System.out.println(username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails user = User.withUsername(username)
                        .password("")  // password not needed here
                        .roles("USER")
                        .build();

                if (jwtUtil.validateToken(token)) {
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}