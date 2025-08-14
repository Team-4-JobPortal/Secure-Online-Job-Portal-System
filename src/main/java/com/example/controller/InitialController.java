package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InitialController {
	@RequestMapping("/")
	public String start() {
		return "index";
	}
	
	@RequestMapping("/login")
	public String Login() {
		return "Login";
	}
	
	@RequestMapping("/register")
	public String Register() {
		return "register";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
	    session.invalidate();
	    return "redirect:/login";
	}

}
