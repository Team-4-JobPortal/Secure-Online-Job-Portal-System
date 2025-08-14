package com.example.service;
 
import java.util.List;
import com.example.entity.User;
 
public interface UserService {
    List<User> findAllUsers();
    User findByUserId(int id);
    List<User> findByUserRole(String role);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(int id);
    User findByEmail(String email);
}