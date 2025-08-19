package com.example.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(JobNotFoundException.class)
	public ResponseEntity<Object> handleJobNotFound(JobNotFoundException ex){
		Map<String, Object> response = new HashMap<>();
		response.put("status", "error");
		response.put("message", ex.getMessage());
		
		return  ResponseEntity
		           .status(HttpStatus.NOT_FOUND)
		           .body(response);
	}
	
	
	@ExceptionHandler(UserEmailNotFoundException.class)
	public ResponseEntity<Object> EmailNotFound(UserEmailNotFoundException ex){
		Map<String, Object> response = new HashMap<>();
		response.put("status", "error");
		response.put("message", ex.getMessage());
		
		return  ResponseEntity
		           .status(HttpStatus.NOT_FOUND)
		           .body(response);
	}
	
	
//	@ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleGeneral(Exception ex) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        body.put("error", "Internal Server Error");
//        body.put("message", ex.getMessage());
//
//        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
