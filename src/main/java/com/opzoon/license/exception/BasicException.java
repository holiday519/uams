package com.opzoon.license.exception;

public class BasicException extends RuntimeException {
	
	private String errorMessage;
	
	public BasicException() {
		this.errorMessage = "";
	}
	
	public BasicException(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public BasicException(Exception e) {
		this.errorMessage = e.getMessage();
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
}
