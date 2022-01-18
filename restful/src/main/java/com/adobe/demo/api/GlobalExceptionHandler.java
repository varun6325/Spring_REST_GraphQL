package com.adobe.demo.api;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("message", ex.getMessage());
		body.put("timestamp", new Date());
		return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		List<String> errorMessages = ex.getBindingResult().getFieldErrors()
			.stream()
			.map(exception -> exception.getDefaultMessage())
			.collect(Collectors.toList());
		
		body.put("errors", errorMessages);
		body.put("timestamp", new Date());
		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}
}
