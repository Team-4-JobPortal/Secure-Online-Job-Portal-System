package com.example.service.impl;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.ApplicationDAO;
import com.example.entity.Application;
import com.example.service.ApplicationService;
 
@Service
public class ApplicationServiceImpl implements ApplicationService{
@Autowired
private ApplicationDAO applicationDAO;
 
@Override
public List<Application> findAllApp() {
	// TODO Auto-generated method stub
	return applicationDAO.findAll();
}
 
@Override
public Application findAppById(int id) {
	// TODO Auto-generated method stub
	return applicationDAO.findById(id);
}
 
@Override
public void saveApp(Application application) {
	// TODO Auto-generated method stub
	applicationDAO.save(application);
}
 
@Override
public void updateApp(Application application) {
	// TODO Auto-generated method stub
	applicationDAO.update(application);
}
 
@Override
public void deleteApp(int id) {
	// TODO Auto-generated method stub
	applicationDAO.delete(id);
}


 
}