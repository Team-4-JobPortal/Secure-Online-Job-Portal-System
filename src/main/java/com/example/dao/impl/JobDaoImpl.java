package com.example.dao.impl;

import com.example.dao.JobDao;
import com.example.entity.Job;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;

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
    public List<Job> findByEmployerEmail(String email) {
        String hql = "FROM Job j WHERE j.user.email = :email";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Job.class)
                .setParameter("email", email)
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

//    @Override
//    public List<Job> searchJobs(String keyword, String location, Integer minSalary, Integer maxSalary) {
//        String hql = "FROM Job j WHERE (:keyword IS NULL OR j.title LIKE :keyword) " +
//                     "AND (:location IS NULL OR j.location = :location) " +
//                     "AND (:minSalary IS NULL OR j.min_salary >= :minSalary) " +
//                     "AND (:maxSalary IS NULL OR j.max_salary <= :maxSalary)";
//
//        return sessionFactory.getCurrentSession()
//                .createQuery(hql, Job.class)
//                .setParameter("keyword", keyword != null ? "%" + keyword + "%" : null)
//                .setParameter("location", location)
//                .setParameter("minSalary", minSalary)
//                .setParameter("maxSalary", maxSalary)
//                .list();
//    }
    
    @Override
    public List<Job> searchJobs(String keyword, String location, Integer minSalary, Integer maxSalary) {
    		Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Job> cq = cb.createQuery(Job.class);
        Root<Job> job = cq.from(Job.class);

        List<Predicate> predicates = new ArrayList<>();

        // Keyword search (in job title)
        if (keyword != null && !keyword.trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(job.get("title")), "%" + keyword.toLowerCase() + "%"));
        }

        // Location filter
        if (location != null && !location.trim().isEmpty()) {
            predicates.add(cb.equal(cb.lower(job.get("location")), location.toLowerCase()));
        }

        // Minimum salary filter
        if (minSalary != null) {
            predicates.add(cb.greaterThanOrEqualTo(job.get("min_salary"), minSalary));
        }

        // Maximum salary filter
        if (maxSalary != null) {
            predicates.add(cb.lessThanOrEqualTo(job.get("max_salary"), maxSalary));
        }

        // Apply filters
        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        // Final query execution
        return session.createQuery(cq).getResultList();   	
        
    }

}
