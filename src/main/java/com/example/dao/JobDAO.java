package com.example.dao;

import java.util.List;

import com.example.entity.Job;

public interface JobDAO {
    List<Job> findAll();
    Job findById(int id);
    void save(Job job);
    void update(Job job);
    void delete(int id);
}
