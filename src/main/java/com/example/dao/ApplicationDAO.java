package com.example.dao;

import java.util.List;

import com.example.entity.Application;

public interface ApplicationDAO {
    List<Application> findAll();
    Application findById(int id);
    void save(Application application);
    void update(Application application);
    void delete(int id);
}