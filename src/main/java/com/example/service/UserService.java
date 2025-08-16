package com.example.service;
 
import java.util.List;
import com.example.entity.User;
 
public interface UserService {
    List<User> findAllUsers();
    User findByUserId(int id);
    List<User> findByUserRole(String role);
    User findByemail(String email);
    void saveUser(User user);
    void updateUser(User user);
    void updatePassword(User dbUser, String newPassword);
    void deleteUser(int id);
}