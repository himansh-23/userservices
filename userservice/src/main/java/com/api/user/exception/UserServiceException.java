package com.api.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.api.user.response.Response;

@ControllerAdvice
public class UserServiceException {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> exceptionResolver(Exception e)
	{
		Response response=new Response();
		response.setStatusCode(100);
		response.setStatusMessage(e.getMessage());
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<Response> exceptionResolver(UserException e)
	{
		Response response =new Response();
		response.setStatusCode(123);
		response.setStatusMessage(e.getMessage());
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
}
