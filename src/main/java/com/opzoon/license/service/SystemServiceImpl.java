package com.opzoon.license.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.util.WebUtils;

import com.opzoon.license.dao.SystemDao;
import com.opzoon.license.domain.PageModel;
import com.opzoon.license.domain.User;
import com.opzoon.license.domain.request.LoginRequest;
import com.opzoon.license.domain.request.PasswordRequest;
import com.opzoon.license.domain.request.UserRequest;
import com.opzoon.license.domain.response.BasicResponse;
import com.opzoon.license.domain.response.LoginResponse;
import com.opzoon.license.domain.response.UserResponse;
import com.opzoon.license.exception.BasicException;
import com.opzoon.license.exception.ExceptionCode;
import com.opzoon.license.exception.custom.PasswordIncorrectException;
import com.opzoon.license.exception.custom.UserNotFoundException;

@Service("systemService")
public class SystemServiceImpl implements SystemService {
	
	private static Logger log = Logger.getLogger(SystemServiceImpl.class); 
	
	@Autowired
	private SystemDao systemDao;
	
	@Override
	public BasicResponse loginUser(LoginRequest login) throws BasicException {
		log.info("<=license=>service LoginRequest login=" + login);
		User user = new User();
		user.setUserName(login.getUserName());
		List<User> userList = systemDao.findUsers(new UserRequest(user, new PageModel()));
		if(userList.isEmpty()) {
			throw new UserNotFoundException();
		}
		if(!DigestUtils.md5DigestAsHex(login.getPassword().getBytes()).equals(userList.get(0).getPassword())) {
			throw new PasswordIncorrectException();
		}
		return new LoginResponse(ExceptionCode.Success.getErrorCode(), userList.get(0));
	}
	
	@Override
	public BasicResponse logoutUser(HttpServletRequest request) throws BasicException {
		log.info("<=license=>service logoutUser");
		WebUtils.setSessionAttribute(request, "loginuser", null);
		return new BasicResponse(ExceptionCode.Success.getErrorCode());
	}

	@Override
	@Transactional
	public BasicResponse createUser(User user) throws BasicException {
		log.info("<=license=>service createUser user=" + user);
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		systemDao.addUser(user);
		return new BasicResponse(ExceptionCode.Success.getErrorCode());
	}
	
	@Override
	public BasicResponse listUsers(UserRequest userReq) throws BasicException {
		log.info("<=license=>service listUsers userRequest=" + userReq);
		List<User> userList = systemDao.findUsers(userReq);
		PageModel pageModel = userReq.getPage();
		pageModel.setAmount(systemDao.getUserAmount(userReq));
		return new UserResponse(ExceptionCode.Success.getErrorCode(), userList, pageModel);
	}

	@Override
	public BasicResponse updateUser(User user) throws BasicException {
		log.info("<=license=>service updateUser user=" + user);
		systemDao.updateUser(user);
		return new BasicResponse(ExceptionCode.Success.getErrorCode());
	}

	@Override
	public BasicResponse deleteUsers(Integer[] ids) throws BasicException {
		log.info("<=license=>service deleteUsers ids=" + ids);
		systemDao.deleteUsers(ids);
		return new BasicResponse(ExceptionCode.Success.getErrorCode());
	}

	@Override
	public BasicResponse modifyPassword(PasswordRequest passwordReq) throws BasicException {
		log.info("<=license=>service modifyPassword passwordReq=" + passwordReq);
		passwordReq.setPassword(DigestUtils.md5DigestAsHex(passwordReq.getPassword().getBytes()));
		systemDao.modifyPassword(passwordReq);
		return new BasicResponse(ExceptionCode.Success.getErrorCode());
	}
	
}
