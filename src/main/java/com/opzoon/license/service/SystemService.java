package com.opzoon.license.service;

import javax.servlet.http.HttpServletRequest;

import com.opzoon.license.domain.User;
import com.opzoon.license.domain.request.LoginRequest;
import com.opzoon.license.domain.request.PasswordRequest;
import com.opzoon.license.domain.request.UserRequest;
import com.opzoon.license.domain.response.BasicResponse;
import com.opzoon.license.exception.BasicException;

public interface SystemService {
	
	public BasicResponse loginUser(LoginRequest login) throws BasicException;
	
	public BasicResponse logoutUser(HttpServletRequest request) throws BasicException;
	
	public BasicResponse createUser(User user) throws BasicException;
	
	public BasicResponse listUsers(UserRequest userReq) throws BasicException;
	
	public BasicResponse updateUser(User user) throws BasicException;
	
	public BasicResponse deleteUsers(Integer[] ids) throws BasicException;
	
	public BasicResponse modifyPassword(PasswordRequest passwordReq) throws BasicException;
	// 表单验证
	//public BasicResponse validation() throws BasicException;
}
