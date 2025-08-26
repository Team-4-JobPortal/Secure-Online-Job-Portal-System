package com.example.dao.impl;

import com.example.dao.JobHistroyDao;
import com.example.entity.JobHistory;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public  class JobHistoryDaoImpl implements JobHistroyDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(JobHistory jobHistory) {
        sessionFactory.getCurrentSession().save(jobHistory);
    }


    @Override
    public List<JobHistory> list() {
        return sessionFactory.getCurrentSession()
                .createQuery("from JobHistory", JobHistory.class)
                .list();
    }

	
    
}
