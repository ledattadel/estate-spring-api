package com.laptrinhjavaweb.controlleradvice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.laptrinhjavaweb.dto.ResponeDTO;
import com.laptrinhjavaweb.myexception.FieldRequiredException;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler{
	@ExceptionHandler(FieldRequiredException.class)
	public ResponseEntity<ResponeDTO> handleCityNotFoundException(FieldRequiredException ex, WebRequest request){
		
		
		ResponeDTO result = new ResponeDTO();
		result.setData(null);
		List<String> errors = new ArrayList<>();
		errors.add(ex.getMessage());
		result.setError(errors);
		// return eror details in the response body
		// pay attention to status codes
		
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
}
