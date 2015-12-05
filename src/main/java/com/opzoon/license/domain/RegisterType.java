package com.opzoon.license.domain;

import com.opzoon.license.exception.custom.EnumtypeNotExistExecption;

public enum RegisterType {
	UID(1), SN(2);
	
	private int code;
	
	public int getCode() {
		return code;
	}
	
	public static RegisterType getRegisterType(int code) {
		for(RegisterType type : RegisterType.values()) {
			if(type.getCode() == code) {
				return type;
			}
		}
		throw new EnumtypeNotExistExecption();
	}

	public void setCode(int code) {
		this.code = code;
	}

	private RegisterType(int code) {
		this.code = code;
	}
	
}
