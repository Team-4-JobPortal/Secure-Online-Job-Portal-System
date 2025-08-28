package com.example.dto;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.sql.Date;
import java.time.LocalDateTime;

public class JobDto {

    @NotBlank(message = "title is required")
	@Size(min=3,max=150,message="Title must be atleast 3 characters") 
    private String title;

    @NotBlank(message="Description is required")
	@Size(max=1000,message="Description can't be greater than 1000 characters")
    private String description;

    @NotBlank(message="location is required")
    private String location;
    
    @NotNull(message = "min_salary cannot be null")
    @Min(value = 0, message = "Minimum salary must be non-negative")
    private int min_salary;
    
    @NotNull(message = "max_salary cannot be null")
    @Min(value = 0, message = "Maximum salary must be non-negative")
    private int max_salary;

    private Boolean status;
    
    private LocalDateTime createdAt;
    @NotNull(message = "Application deadline is required")
    private Date deadline;


    // --- Getters & Setters ---

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
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
}
