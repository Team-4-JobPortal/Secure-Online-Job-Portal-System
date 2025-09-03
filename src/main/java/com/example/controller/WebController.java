package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Web controller for serving HTML templates.
 * Handles the main web pages and user interface routes.
 */
@Controller
public class WebController {

    /**
     * Serve the home page
     * @return index.html template
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /**
     * Serve the login page
     * @return login.html template
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Serve the registration page
     * @return register.html template
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * Serve the candidate dashboard
     * @return candidate-dashboard.html template
     */
    @GetMapping("/candidateDashboard")
    public String candidateDashboard() {
        return "CandidateDashboard";
    }

    /**
     * Serve the employer dashboard
     * @return employer-dashboard.html template
     */
    @GetMapping("/employerDashboard")
    public String employerDashboard() {
        return "EmployerDashboard";
    }
    
    @GetMapping("/forgetPassword")
    public String changePassword() {
        return "EmployerDashboard";
    }

    
}
