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

/**
 * JWT Authentication filter that validates JWT tokens on each request.
 * Extracts JWT from Authorization header and sets authentication in security context.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * Filters incoming HTTP requests to validate JWT tokens.
     * Skips authentication for public endpoints.
     * Sets security context if valid JWT token is present.
     * @param request HTTP servlet request containing headers and path
     * @param response HTTP servlet response for sending data back
     * @param filterChain Chain of filters to continue processing
     * @throws ServletException if servlet processing error occurs
     * @throws IOException if input/output error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {
    
        String path = request.getRequestURI();
        
        // Skip JWT validation for public authentication endpoints
        if (path.startsWith("/auth/") || path.startsWith("/register") || path.startsWith("/users/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        
        // Extract and validate JWT token from Authorization header
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            
            try {
                String username = jwtUtil.getUsernameFromToken(token);
                
                // Set authentication in security context if token is valid
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    
                    if (jwtUtil.validateToken(token)) {
                        UsernamePasswordAuthenticationToken auth = 
                                new UsernamePasswordAuthenticationToken(
                                    userDetails, 
                                    null, 
                                    userDetails.getAuthorities()
                                );
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            } catch (Exception e) {
                logger.error("Cannot set user authentication: {}", e);
            }
        }

        filterChain.doFilter(request, response);
    }
}