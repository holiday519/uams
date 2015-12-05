package com.opzoon.license.domain;

import com.opzoon.license.exception.custom.EnumtypeNotExistExecption;

public enum UsageType {
	FORMAT(1), TRIAL(2);
	
	private int code;
	
	private UsageType(int code) {
		this.code = code;
	}
	
	public static UsageType getUsageType(int code) {
		for(UsageType type : UsageType.values()) {
			if(type.getCode() == code) {
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
