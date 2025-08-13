package com.example.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.JobDAO;
import com.example.dao.UserDAO;
import com.example.entity.Job;
import com.example.entity.User;

@Repository
@Transactional
public class JobDAOImpl implements JobDAO {
    
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Job> findAll() {
        return getSession().createQuery("FROM Job", Job.class).getResultList();
    }

    @Override
    public Job findById(int id) {
        return getSession().get(Job.class, id);
    }

    @Override
    public void save(Job job) {
        getSession().save(job);
    }

    @Override
    public void update(Job job) {
        getSession().update(job);
    }

    @Override
    public void delete(int id) {
        Job job = findById(id);
        if (job != null) {
            getSession().delete(job);
        }
    }


}
