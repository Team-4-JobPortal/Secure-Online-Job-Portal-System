package com.example.dao;

import java.util.List;

import com.example.entity.Job;

public interface JobDao {

    void save(Job job);

    Job get(int id);

    List<Job> list();
    
    List<Job> findByEmployerEmail(String email);


    void update(Job job);

    void delete(int id);

    List<Job> searchJobs(String keyword, String location, Integer minSalary, Integer maxSalary);

	List<Job> findByEmployerUserid(int user_id);
}
