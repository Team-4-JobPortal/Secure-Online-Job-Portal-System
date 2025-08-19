package com.example.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(JobNotFoundException.class)
	    public ResponseEntity<Map<String, Object>> handleJobNotFound(JobNotFoundException ex) {
		 	Map<String, Object> response = new HashMap<>();
		 	response.put("status", "error");
		 	response.put("message", ex.getMessage());
		 	
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	 
	   // Handle validation errors (from @Valid annotated inputs)
	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getFieldErrors().forEach(error -> 
	            errors.put(error.getField(), error.getDefaultMessage())
	        );
	        return ResponseEntity.badRequest().body(errors);
	    }

	    // Handle generic IllegalArgumentException
	    @ExceptionHandler(IllegalArgumentException.class)
	    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
	        return ResponseEntity.badRequest().body(ex.getMessage());
	    }

	    // Handle all other unhandled exceptions (fallback)
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleGeneralException(Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Something went wrong: " + ex.getMessage());
	    }

}