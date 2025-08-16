package com.example.controller;
 
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.entity.Application;

import com.example.service.ApplicationService;
 
@RestController
@RequestMapping("/applications")
public class ApplicationController {
    
    @Autowired
    private ApplicationService appService;
    
 
    @GetMapping("/list")
    public List<Application> listUsers() {
        return appService.findAllApp();
    }
    
    @GetMapping("/{id}")
    public Application GetByAppId(@PathVariable int id) {
    	return appService.findAppById(id);
    }
 
    @PostMapping("/apply")
    public void saveApp(@RequestBody Application app) {
    	
    	appService.saveApp(app);
    }
    
    
    @PutMapping("/{id}")
    public void updateApp(@PathVariable int id, @RequestBody Application app) {
    	app.setApplication_id(id);
        appService.updateApp(app);
    }
    
    
    @DeleteMapping("/{id}")
    public void deleteApp(@PathVariable int id) {
    	appService.deleteApp(id);
    }

}