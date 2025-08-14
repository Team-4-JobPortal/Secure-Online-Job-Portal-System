package com.example.dao;

import java.util.List;
import com.example.entity.User;

public interface UserDAO {
    List<User> findAll();
    User findById(int id);
    List<User> findByRole(String role); 
    void save(User user);
    void update(User user);
    void delete(int id);
    User findByEmail(String email);
}