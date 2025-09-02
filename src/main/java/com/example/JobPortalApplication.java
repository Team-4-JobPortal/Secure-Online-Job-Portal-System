package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application class for the Job Portal System.
 * This class serves as the entry point for the application and enables
 * auto-configuration, component scanning, and configuration properties.
 */
@SpringBootApplication
public class JobPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobPortalApplication.class, args);
    }
}
