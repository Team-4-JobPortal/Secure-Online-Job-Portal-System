package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
    
        String path = request.getRequestURI();
        
        // ✅ Only skip JWT validation for login and register endpoints
        if (path.endsWith("/auth/login") || path.endsWith("/auth/register") || 
            path.endsWith("/register") || path.endsWith("/login") || 
            path.endsWith("/") || path.contains("/css/") || 
            path.contains("/js/") || path.contains("/images/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        
        logger.debug("Processing request: " + path);
        logger.debug("Authorization header: " + header);
        
        // ✅ Extract JWT token from Authorization header
        if (header != null && header.startsWith("Bearer ")) {
            jwt = header.substring(7);
            try {
                username = jwtUtil.getUsernameFromToken(jwt);
                logger.debug("Extracted username from token: " + username);
            } catch (Exception e) {
                logger.error("Cannot extract username from token: " + e.getMessage());
            }
        } else {
            logger.warn("No Authorization header found or doesn't start with Bearer");
        }
        
        // ✅ Set authentication in security context if token is valid
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                if (jwtUtil.validateToken(jwt)) {
                    UsernamePasswordAuthenticationToken auth = 
                            new UsernamePasswordAuthenticationToken(
                                userDetails, 
                                null, 
                                userDetails.getAuthorities()
                            );
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    logger.debug("Authentication set for user: " + username);
                } else {
                    logger.warn("Token validation failed for user: " + username);
                }
            } catch (Exception e) {
                logger.error("Cannot set user authentication: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
