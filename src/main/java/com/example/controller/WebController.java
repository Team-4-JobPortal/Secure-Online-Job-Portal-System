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
    @GetMapping("/candidate-dashboard")
    public String candidateDashboard() {
        return "candidate-dashboard";
    }

    /**
     * Serve the employer dashboard
     * @return employer-dashboard.html template
     */
    @GetMapping("/employer-dashboard")
    public String employerDashboard() {
        return "employer-dashboard";
    }

    /**
     * Serve the job details page
     * @return job-details.html template
     */
    @GetMapping("/job-details")
    public String jobDetails() {
        return "job-details";
    }
}
