package com.example.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.entity.User;
import com.example.service.UserService;

@RestController
@RequestMapping("/users")
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
    public User getByUserId(@PathVariable int id) {
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
}
