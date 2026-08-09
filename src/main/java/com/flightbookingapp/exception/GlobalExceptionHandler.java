package com.flightbookingapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.flightbookingapp.dto.ErrorResponse;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler  {
	
	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<ErrorResponse<String>> handleInvalidDataException(InvalidDataException e) {
		ErrorResponse<String> res = new ErrorResponse<String>();
		
		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setMessage(e.getMessage());
		res.setData("Error");
		
		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
	}
}
