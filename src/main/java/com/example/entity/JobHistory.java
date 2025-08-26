package com.example.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "JobHistorys")
public class JobHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private int job_id;

    @NotBlank(message = "title is required")
	@Size(min=3,max=150,message="Title must be atleast 3 characters") 
    private String title;

    @NotBlank(message="Description is required")
	@Size(max=1000,message="Description can't be greater than 1000 characters")
    private String description;

    @NotBlank(message="location is required")
    private String location;

   
    @Column(name = "min_salary")
    private int min_salary;

    
    @Column(name = "max_salary")
    private int max_salary;

    
    @Column(name = "posting_date")
    private LocalDateTime createdAt;
    @NotNull
    @Column(name = "application_deadline")
    private Date deadline;

    
    // --- Getters & Setters ---
    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMin_salary() {
        return min_salary;
    }

    public void setMin_salary(int min_salary) {
        this.min_salary = min_salary;
    }

    public int getMax_salary() {
        return max_salary;
    }

    public void setMax_salary(int max_salary) {
        this.max_salary = max_salary;
    }

    public  LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime localDateTime) {
        this.createdAt = localDateTime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

}
