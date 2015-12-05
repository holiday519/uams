package com.opzoon.license.exception;

import java.io.EOFException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonProcessingException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.opzoon.license.exception.custom.EnumtypeNotExistExecption;
import com.opzoon.license.exception.custom.PasswordIncorrectException;
import com.opzoon.license.exception.custom.ResourceConflictException;
import com.opzoon.license.exception.custom.ResourceNotFoundException;
import com.opzoon.license.exception.custom.RsaException;
import com.opzoon.license.exception.custom.UserNotFoundException;

public class ExceptionManager {

	private static Logger log = Logger.getLogger(ExceptionManager.class);
	
	public static int convertToCode(Exception e) {
		log.error("<=license=> exception : [" + e.getMessage() + "]");
		if (e instanceof NullPointerException || e instanceof ClassCastException || 
				e instanceof ClassNotFoundException || e instanceof NoSuchFieldException || 
				e instanceof IndexOutOfBoundsException || e instanceof IllegalArgumentException ||
				e instanceof IllegalAccessException || e instanceof EnumtypeNotExistExecption) {
			
			return ExceptionCode.ServerAbnormalException.getErrorCode();
			
		} else if (e instanceof EOFException || e instanceof JsonProcessingException || 
				e instanceof MethodArgumentNotValidException || e instanceof HttpMessageNotReadableException) {
			
			return ExceptionCode.BadRequestException.getErrorCode();
			
		} else if (e instanceof UserNotFoundException) {
			
			return ExceptionCode.UserNotFoundException.getErrorCode();
			
		} else if (e instanceof PasswordIncorrectException) {
			
			return ExceptionCode.PasswordIncorrectException.getErrorCode();
			
		} else if(e instanceof ResourceConflictException) {
			
			return ExceptionCode.ResourceConflictException.getErrorCode();
			
		} else if(e instanceof ResourceNotFoundException) {
			
			return ExceptionCode.ResourceNotFoundException.getErrorCode();
			
		} else if(e instanceof RsaException) {
			
			return ExceptionCode.InvalidKeyException.getErrorCode();
			
		} else {
			
			return ExceptionCode.UnknowException.getErrorCode();
			
		}
	}
	
}
