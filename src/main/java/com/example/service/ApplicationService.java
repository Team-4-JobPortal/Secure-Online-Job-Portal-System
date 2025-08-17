package com.example.service;
 
import java.util.List;
 
import com.example.entity.Application;
 
public interface ApplicationService {
    List<Application> findAllApp();
    Application findAppById(int id);
    void saveApp(Application application);
    void updateApp(Application application);
    void deleteApp(int id);
    
    // NEW METHODS
    List<Application> findApplicationsByUser(int userId);
    boolean hasUserAppliedToJob(int userId, int jobId);
}