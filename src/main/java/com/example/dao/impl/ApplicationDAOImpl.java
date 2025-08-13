package com.example.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.ApplicationDAO;
import com.example.entity.Application;

@Repository
@Transactional

public class ApplicationDAOImpl implements ApplicationDAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public List<Application> findAll(){
		return getSession().createQuery("FROM Application", Application.class).getResultList();
	}
	
	
	@Override
	public Application findById(int id) {
		return getSession().get(Application.class, id);
	}


	@Override
	public void save(Application application) {
		 getSession().save(application);
		
	}

	@Override
	public void update(Application application) {
		 getSession().update(application);
		
	}

	@Override
	public void delete(int id) {
		Application application = findById(id);
		if(application!=null) {
			getSession().delete(application);
		}
		
	}
}
