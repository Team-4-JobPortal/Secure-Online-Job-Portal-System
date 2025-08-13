package com.example.service;

import com.example.entity.Job;
import java.util.List;

public interface JobService {

    void createJob(Job job);

    Job getJobById(int id);

    List<Job> getAllJobs();

    void updateJob(Job job);

    void deleteJob(int id);

    List<Job> searchJobs(String keyword, String location, Integer minSalary, Integer maxSalary);
}
