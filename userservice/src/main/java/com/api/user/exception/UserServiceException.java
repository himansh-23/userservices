package com.api.user.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class UserServiceException {

	@ExceptionHandler(Exception.class)
	public void exceptionResolver(Exception e)
	{
		//e.printStackTrace();
	//	System.out.println(e.getMessage());
	//	System.exit(1);
	}
}
