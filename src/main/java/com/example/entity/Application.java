package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="Applications")
public class Application {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int application_id;
	private String status;
	@ManyToMany
	@JoinColumn(name = "candidate_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "job_id")
	private Job job;
}
