package com.devsuperior.bds04.controllers.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validationException(MethodArgumentNotValidException exception,
			HttpServletRequest request) {
		
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY; //422 

		ValidationError validationError = new ValidationError();
		validationError.setTimestamp(Instant.now());
		validationError.setStatus(status.value());
		validationError.setError("Validation exception");
		validationError.setMessage(exception.getMessage());
		validationError.setPath(request.getRequestURI());
		
		//acessa a lista de errors interna do beans valitaion
		for( FieldError fieldError : exception.getBindingResult().getFieldErrors()){
			validationError.addError(fieldError.getField() , fieldError.getDefaultMessage());
		}

		return ResponseEntity.status(status).body(validationError);

	}


}
