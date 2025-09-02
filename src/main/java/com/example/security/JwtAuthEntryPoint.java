package com.example.security;



import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/* 
 * Entry point for handling authentication failures.
 * Sends 401 Unauthorized response when authentication is required but missing/invalid.
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    
    /* 
     * Handles unauthorized access attempts by sending HTTP 401 error.
     * Called when user tries to access protected resource without valid authentication.
     * @param request HTTP servlet request that resulted in authentication exception
     * @param response HTTP servlet response to send error back to client
     * @param authException Exception that caused authentication failure
     * @throws IOException if input/output error occurs while sending response
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid or missing token");
    }
}
