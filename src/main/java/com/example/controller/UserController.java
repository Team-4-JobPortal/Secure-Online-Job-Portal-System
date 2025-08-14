package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.entity.User;
import com.example.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    

    @GetMapping
    public List<User> listUsers() {
        return userService.findAllUsers();
    }
    

    @GetMapping("/role/{role}")
    public List<User> listUsersByRole(@PathVariable String role) {
        return userService.findByUserRole(role);
    }
    
    @GetMapping("/{id}")
    public User GetByUserId(@PathVariable int id) {
    	 return userService.findByUserId(id);
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
    	userService.saveUser(user);
    }
    
    
    @PutMapping("/{id}")
    public void updateUser(@PathVariable int id, @RequestBody User user) {
    	user.setUser_id(id);
        userService.updateUser(user);
    }
    

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
    	userService.deleteUser(id);
    }
    
    @PostMapping("/login")
    public Map<String, String> loginUser(@RequestBody Map<String, String> credentials, HttpSession session) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        String role = credentials.get("role");
        System.out.println(role);

        Map<String, String> response = new HashMap<>();

        User user = userService.findByEmail(email); // You need to implement this in UserService

        if (user == null) {
            response.put("status", "fail");
            response.put("message", "Invalid email address");
            return response;
        }
        
        System.out.println("Found user: " + user.getEmail() + " role: " + user.getRole());


        // If you store hashed passwords, use BCrypt or similar for checking
        if (user.getPassword() == null || !user.getPassword().equals(password)) {
            response.put("status", "fail");
            response.put("message", "Wrong password");
            return response;
        }
        
        if (role == null || !user.getRole().equalsIgnoreCase(role)) {
            response.put("status", "fail");
            response.put("message", "Access denied for role: " + role);
            return response;
        }
         	
        session.setAttribute("loggedInUser", user.getEmail());
        session.setAttribute("role", user.getRole());
       

        response.put("status", "success");
        response.put("message", "Login successful");
        response.put("role", user.getRole()); 
        return response;
    }

}