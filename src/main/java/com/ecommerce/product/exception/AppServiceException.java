package com.ecommerce.product.exception;

import lombok.Data;

@Data
public class AppServiceException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode;
	
	public AppServiceException(String message, String errCode) {
		super(message);
		errorCode = errCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}
