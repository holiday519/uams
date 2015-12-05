package com.opzoon.license.domain;

import java.util.Set;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;


public class User {

	private Integer userId;
	@NotEmpty
	private String userName;
	
	private String password;
	
	private String telephone;
	@Email
	private String email;
	
	private Set<Product> products;
	@Range(max=2, min=1)
	private Integer userTypeCode;
	
	private String note;
	
	public User() {
		
	}

	public User(Integer userId, String userName, String password, String telephone, String email, Set<Product> productSet, UserType userType, String note) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.telephone = telephone;
		this.email = email;
		this.products = productSet;
		this.userTypeCode = userType.getCode();
		this.note = note;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Integer getUserTypeCode() {
		return userTypeCode;
	}

	public void setUserTypeCode(Integer userTypeCode) {
		this.userTypeCode = userTypeCode;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + "]";
	}
	
}
