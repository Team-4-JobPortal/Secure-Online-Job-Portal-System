package com.example.service;

import java.util.List;

import com.example.entity.JobHistory;

public interface JobHistoryService {
	   void save(JobHistory job);
	    List<JobHistory> list();
}
