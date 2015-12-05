package com.opzoon.license.dao;

import java.util.List;

import com.opzoon.license.domain.User;
import com.opzoon.license.domain.request.PasswordRequest;
import com.opzoon.license.domain.request.UserRequest;
import com.opzoon.license.exception.BasicException;

public interface SystemDao {

	public void addUser(User user) throws BasicException;
	
	public List<User> findUsers(UserRequest userReq) throws BasicException;
	
	public void updateUser(User user) throws BasicException;
	
	public void deleteUsers(Integer[] ids) throws BasicException;
	
	public int getUserAmount(UserRequest userReq) throws BasicException;
	
	public void modifyPassword(PasswordRequest passwordReq) throws BasicException;
	
}
