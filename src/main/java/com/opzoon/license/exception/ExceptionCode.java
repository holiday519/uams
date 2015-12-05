package com.opzoon.license.exception;

public enum ExceptionCode {
	
	// 请求成功
	Success(ERROR_CODE.SUCCESS),
	// 未知错误
	UnknowException(ERROR_CODE.UNKNOW),
	// 请求参数错误
	BadRequestException(ERROR_CODE.BAD_REQUEST),
	// 找不到指定资源
	ResourceNotFoundException(ERROR_CODE.RESOURCE_NOTFOUND),
	// 资源冲突
	ResourceConflictException(ERROR_CODE.RESOURCE_CONFLICT),
	// 服务器异常
	ServerAbnormalException(ERROR_CODE.SERVER_ABNORMAL),
	// 该用户不存在
	UserNotFoundException(ERROR_CODE.USER_NOTFOUND),
	// 用户密码错误
	PasswordIncorrectException(ERROR_CODE.PASSWORD_INCORRECT),
	// session过期
	SessionOverdueException(ERROR_CODE.SESSION_OVERDUE),
	// 非法的ukey文件
	InvalidKeyException(ERROR_CODE.INVALID_KEY);
	
	private int errorCode;
	
	private ExceptionCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	private final static class ERROR_CODE {
		
		public final static int SUCCESS = 000;
		
		public final static int UNKNOW = 101;
		
		public final static int BAD_REQUEST = 102;
		
		public final static int RESOURCE_NOTFOUND = 103;
		
		public final static int RESOURCE_CONFLICT = 104;
		
		public final static int SERVER_ABNORMAL = 105;
		
		public final static int USER_NOTFOUND = 106;
		
		public final static int PASSWORD_INCORRECT = 107;
		
		public final static int SESSION_OVERDUE = 108;
		
		public final static int INVALID_KEY = 109;
	}

	public int getErrorCode() {
		return errorCode;
	}
	
}
