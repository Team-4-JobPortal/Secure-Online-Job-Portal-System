package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/candidateDashboard")
    public String candidateDashboard(HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login"; // Force login if not logged in
        }
        model.addAttribute("loggedInUser", session.getAttribute("loggedInUser"));
        model.addAttribute("role", session.getAttribute("role"));
        return "candidateDashboard"; // candidateDashboard.jsp
    }

    @GetMapping("/employerDashboard")
    public String employerDashboard(HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        model.addAttribute("loggedInUser", session.getAttribute("loggedInUser"));
        model.addAttribute("role", session.getAttribute("role"));
        return "employerDashboard";
    }
}
