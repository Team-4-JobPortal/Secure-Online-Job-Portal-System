package com.example.dao.impl;

import com.example.dao.ApplicationDAO;
import com.example.entity.Application;
import com.example.helper.HibernateUtil;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApplicationDAOImpl implements ApplicationDAO {

    private final HibernateUtil hibernateUtil;

    @Autowired
    public ApplicationDAOImpl(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    @Override
    public List<Application> findAll() {
        return hibernateUtil.executeReadOnly(session ->
            session.createQuery("FROM Application", Application.class).getResultList()
        );
    }

    @Override
    public Application findById(int id) {
        return hibernateUtil.executeReadOnly(session ->
            session.get(Application.class, id)
        );
    }

    @Override
    public void save(Application application) {
        hibernateUtil.executeInTransaction(session -> {
            session.save(application);
            return null;
        });
    }

    @Override
    public void update(Application application) {
        hibernateUtil.executeInTransaction(session -> {
            session.update(application);
            return null;
        });
    }

    @Override
    public void delete(int id) {
        hibernateUtil.executeInTransaction(session -> {
            Application application = session.get(Application.class, id);
            if (application != null) {
                session.delete(application);
            }
            return null;
        });
    }

    @Override
    public List<Application> findByUserId(int userId) {
        return hibernateUtil.executeReadOnly(session -> {
            String hql = "FROM Application a WHERE a.user.user_id = :userId ORDER BY a.application_id DESC";
            Query<Application> query = session.createQuery(hql, Application.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        });
    }

    @Override
    public boolean existsByUserIdAndJobId(int userId, int jobId) {
        return hibernateUtil.executeReadOnly(session -> {
            String hql = "SELECT COUNT(a) FROM Application a WHERE a.user.user_id = :userId AND a.job.job_id = :jobId";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("userId", userId);
            query.setParameter("jobId", jobId);
            Long count = query.uniqueResult();
            return count != null && count > 0;
        });
    }

    @Override
    public List<Application> findApplicationsByEmployer(int employerId) {
        return hibernateUtil.executeReadOnly(session -> {
            String hql = "FROM Application a WHERE a.job.user.user_id = :employerId ORDER BY a.application_id DESC";
            Query<Application> query = session.createQuery(hql, Application.class);
            query.setParameter("employerId", employerId);
            return query.getResultList();
        });
    }
}
