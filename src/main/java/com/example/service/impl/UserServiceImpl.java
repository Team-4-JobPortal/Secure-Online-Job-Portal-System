package com.example.service.impl;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dao.UserDAO;
import com.example.entity.User;
import com.example.service.UserService;
 
@Service
public class UserServiceImpl implements UserService{
 
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserDAO userDAO;
	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return userDAO.findAll();
	}
 
	@Override
	public User findByUserId(int id) {
		// TODO Auto-generated method stub
		return userDAO.findById(id);
	}
 
	@Override
	public List<User> findByUserRole(String role) {
		// TODO Auto-generated method stub
		return userDAO.findByRole(role);
	}
 
	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		userDAO.save(user);
	}
 
	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		userDAO.update(user);
	}
 
	@Override
	public void deleteUser(int id) {
		// TODO Auto-generated method stub
		userDAO.delete(id);
	}
	
	@Override
	public User findByemail(String email) {
		// add here exception
		return userDAO.findByEmail(email);
	}
	
	@Override
	public void updatePassword(User user, String newPassword) {
	    String encoded = passwordEncoder.encode(newPassword);
//		String encoded = newPassword;
	    user.setPassword(encoded);
	    userDAO.update(user); // use your DAO
	}
	
}
 