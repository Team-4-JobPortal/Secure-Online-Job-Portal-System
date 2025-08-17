package com.example.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
	
	 @Override
	    public List<Application> findByUserId(int userId) {
	        String hql = "FROM Application a WHERE a.user.user_id = :userId ORDER BY a.application_id DESC";
	        Query<Application> query = getSession().createQuery(hql, Application.class);
	        query.setParameter("userId", userId);
	        return query.getResultList();
	    }

	    // NEW: Check if user has already applied to a specific job
	    @Override
	    public boolean existsByUserIdAndJobId(int userId, int jobId) {
	        String hql = "SELECT COUNT(a) FROM Application a WHERE a.user.user_id = :userId AND a.job.job_id = :jobId";
	        Query<Long> query = getSession().createQuery(hql, Long.class);
	        query.setParameter("userId", userId);
	        query.setParameter("jobId", jobId);
	        Long count = query.uniqueResult();
	        return count != null && count > 0;
	    }
}