package com.example.dto;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.time.LocalDateTime;

public class JobHistoryDto {

    private Integer jobId;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 150, message = "Title must be at least 3 characters and no more than 150")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description can't be greater than 1000 characters")
    private String description;

    @NotBlank(message = "Location is required")
    @Size(max = 150, message = "Location must not exceed 150 characters")
    private String location;

    @Min(value = 0, message = "Minimum salary must be non-negative")
    private int minSalary;

    @Min(value = 0, message = "Maximum salary must be non-negative")
    private int maxSalary;

    private LocalDateTime createdAt;

    @NotNull(message = "Application deadline is required")
    private Date deadline;

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
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

	public int getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(int minSalary) {
		this.minSalary = minSalary;
	}

	public int getMaxSalary() {
		return maxSalary;
	}

	public void setMaxSalary(int maxSalary) {
		this.maxSalary = maxSalary;
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