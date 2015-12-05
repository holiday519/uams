package com.opzoon.license.exception.custom;

import com.opzoon.license.exception.BasicException;

public class EnumtypeNotExistExecption extends BasicException {
	
	public EnumtypeNotExistExecption() {
		
	}
	
	public EnumtypeNotExistExecption(String errorMessage) {
		super(errorMessage);
	}
	
	public EnumtypeNotExistExecption(Exception e) {
		super(e);
	}
	
	@Override
	public String getMessage() {
		return "<=license=> Exception: [EnumtypeNotExistExecption] " + getErrorMessage();
	}
}
