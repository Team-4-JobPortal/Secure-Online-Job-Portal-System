package com.example.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
	
	 @ExceptionHandler(JobNotFoundException.class)
	    public ResponseEntity<Map<String, Object>> handleJobNotFound(JobNotFoundException ex) {
		 	Map<String, Object> response = new HashMap<>();
		 	response.put("status", "error");
		 	response.put("message", ex.getMessage());
		 	
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }

}