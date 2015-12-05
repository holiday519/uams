package com.opzoon.license.domain.request;

public class PasswordRequest {

	private Integer userId;
	
	private String password;
	
	public PasswordRequest() {
		
	}
	
	public PasswordRequest(Integer userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "PasswordRequest [userId=" + userId + "]";
	}
	
}
