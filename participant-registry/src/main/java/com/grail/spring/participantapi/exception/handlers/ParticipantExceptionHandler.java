package com.grail.spring.participantapi.exception.handlers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.grail.spring.participantapi.exception.*;

@ControllerAdvice
public class ParticipantExceptionHandler{

	@ExceptionHandler(value = ParticipantNotFoundException.class)
	public ResponseEntity<Object> exception(ParticipantNotFoundException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = PariticpantAlreadyExistException.class)
	public ResponseEntity<Object> exception(PariticpantAlreadyExistException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = ConstraintViolationException.class)
	public ResponseEntity<Object> exception(ConstraintViolationException exception) {
		
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	 
	    ex.getBindingResult().getFieldErrors().forEach(error -> 
	        errors.put(error.getField(), error.getDefaultMessage()));
	     
	   
	    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	 

}
