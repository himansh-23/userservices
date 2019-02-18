package com.api.user.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int errorCode;
	String msg;
	
	public UserException(String errormessage) {
		super(errormessage);
	}
	public UserException(int errorCode,String msg)
	{
		this(msg);
		this.errorCode=errorCode;
		this.msg=msg;
	}
	public UserException(int errorCode, String msg, Throwable throwable)
	{
		super(msg, throwable);
		this.errorCode=errorCode;
		this.msg=msg;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
