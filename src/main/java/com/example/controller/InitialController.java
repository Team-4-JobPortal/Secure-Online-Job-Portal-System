package com.example.controller;

import org.springframework.stereotype.Controller;

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
	
	@RequestMapping("/employerDashboard")
	public String EmployerDashBoard() {
		return "EmployerDashboard";
	}
	
	@RequestMapping("/forgetPassword")
	public String ForgetPassword() {
		return "ForgetPassword";
	}
	
	@RequestMapping("/candidateDashboard")
	public String CandidateDashBoard() {
		return "CandidateDashboard";
	}
	
}
