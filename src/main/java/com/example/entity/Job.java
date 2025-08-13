package com.example.entity;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Jobs")
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int job_id;
	private String title;
	private String description;
	private String location;
	private int min_salary;
	private int max_salary;
	@Column(name = "posting_date")
	private OffsetDateTime createdAt;
	@Column(name = "application_deadline")
	private OffsetDateTime deadline;
	@ManyToOne
	@JoinColumn(name = "employer_id")
	private User user;
}
