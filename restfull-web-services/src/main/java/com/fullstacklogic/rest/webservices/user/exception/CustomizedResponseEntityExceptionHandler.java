package com.fullstacklogic.rest.webservices.user.exception;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fullstacklogic.rest.webservices.user.PostNotFoundException;
import com.fullstacklogic.rest.webservices.user.UserNotFoundException;
 
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
 
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
				ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
 
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
				ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
		
	} 
	
	@ExceptionHandler(PostNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handlePostNotFoundException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
				ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
		
	} 
	
	@Nullable
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
	        MethodArgumentNotValidException ex,
	        HttpHeaders headers,
	        HttpStatusCode status,
	        WebRequest request) {

	    List<String> errorMessages = ex.getFieldErrors().stream()
	            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
	            .toList();

	    String combinedMessage = "Total Errors: " + ex.getErrorCount() + " | " + String.join(" | ", errorMessages);

	    ErrorDetails errorDetails = new ErrorDetails(
	            LocalDateTime.now(),
	            combinedMessage,
	            request.getDescription(false)
	    );

	    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	
}