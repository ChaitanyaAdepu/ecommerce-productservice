package com.ecommerce.product.model;

import java.util.List;

import lombok.Data;

@Data
public class APIResponse<T> {
	
	private String message;
	private String errorMessage;
	private List<T> responseList;
	private String totalItems;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public List<T> getResponseList() {
		return responseList;
	}
	public void setResponseList(List<T> responseList) {
		this.responseList = responseList;
	}
	public String getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(String totalItems) {
		this.totalItems = totalItems;
	}
	
}
