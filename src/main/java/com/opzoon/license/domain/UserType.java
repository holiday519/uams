package com.opzoon.license.domain;

import com.opzoon.license.exception.BasicException;
import com.opzoon.license.exception.custom.EnumtypeNotExistExecption;

public enum UserType {
	SUPER(1), COMMON(2);
	
	private int code;

	private UserType(int code) {
		this.code = code;
	}
	
	/**
	 * @param 枚举的编号
	 * @return 枚举的具体类型
	 * @throws BasicException 
	 */
	public static UserType getUserType(int code) {
		for(UserType type : UserType.values()) {
			if(code == type.getCode()) {
				return type;
			}
		}
		throw new EnumtypeNotExistExecption();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
}
