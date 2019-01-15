package com.api.user.exception;

public class UserException extends Exception {

	int errorCode;
	String msg;
	public UserException()
	{
		
	}
	public UserException(String errormessage) {
		super(errormessage);
	}
	public UserException(int errorCode,String msg)
	{
		this(msg);
		this.errorCode=errorCode;
	}
	public UserException(int errorCode, String msg, Throwable throwable)
	{
		super(msg, throwable);
		this.errorCode=errorCode;
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
