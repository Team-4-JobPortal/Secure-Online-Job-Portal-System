package com.example.dao.impl;

import com.example.dao.JobDao;
import com.example.entity.Job;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public  class JobDaoImpl implements JobDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Job job) {
        sessionFactory.getCurrentSession().save(job);
    }

    @Override
    public Job get(int id) {
        return sessionFactory.getCurrentSession().get(Job.class, id);
    }

    @Override
    public List<Job> list() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Job", Job.class)
                .list();
    }

    @Override
    public void update(Job job) {
        sessionFactory.getCurrentSession().update(job);
    }

    @Override
    public void delete(int id) {
        Job job = get(id);
        if (job != null) {
            sessionFactory.getCurrentSession().delete(job);
        }
    }

    @Override
    public List<Job> searchJobs(String keyword, String location, Integer minSalary, Integer maxSalary) {
        String hql = "FROM Job j WHERE (:keyword IS NULL OR j.title LIKE :keyword) " +
                     "AND (:location IS NULL OR j.location = :location) " +
                     "AND (:minSalary IS NULL OR j.min_salary >= :minSalary) " +
                     "AND (:maxSalary IS NULL OR j.max_salary <= :maxSalary)";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, Job.class)
                .setParameter("keyword", keyword != null ? "%" + keyword + "%" : null)
                .setParameter("location", location)
                .setParameter("minSalary", minSalary)
                .setParameter("maxSalary", maxSalary)
                .list();
    }
}
