package com.example.dao;

import com.example.entity.JobHistory;

import java.util.List;

public interface JobHistroyDao {

    void save(JobHistory job);
    List<JobHistory> list();
    
   
}
