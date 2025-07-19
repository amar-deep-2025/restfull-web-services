package com.fullstacklogic.rest.webservices.user.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fullstacklogic.rest.webservices.user.UserNotFoundException;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
		ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
		
		return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundExceptions(Exception ex, WebRequest request){
		ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
		
		return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
	}
}
