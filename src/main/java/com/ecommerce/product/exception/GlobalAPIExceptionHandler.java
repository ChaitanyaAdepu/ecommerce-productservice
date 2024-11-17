package com.ecommerce.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ecommerce.product.model.ErrorResponse;

@ControllerAdvice
public class GlobalAPIExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(AppServiceException.class)
	public ResponseEntity<ErrorResponse> handleProductServiceException(AppServiceException exception){
		ErrorResponse errResp = new ErrorResponse();
		errResp.setErrorMessage(exception.getMessage());
		errResp.setErrorCode(exception.getErrorCode());
		return new ResponseEntity<>(errResp, HttpStatus.NOT_FOUND);
	}
}
