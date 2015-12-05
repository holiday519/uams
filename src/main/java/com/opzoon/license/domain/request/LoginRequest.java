package com.opzoon.license.domain.request;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginRequest {
	@NotEmpty
	private String userName;
	@NotEmpty
	private String password;
	
	public LoginRequest() {
		
	}
	
	public LoginRequest(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginRequest [userName=" + userName + ", password=" + password + "]";
	}
	
}
