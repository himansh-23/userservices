package com.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.user.entity.User;
import com.api.user.response.FeildErrorResponse;
import com.api.user.response.Response;
import com.api.user.services.PasswordServices;
import com.api.user.services.UserServices;

@RequestMapping("/api/user")
@RestController
public class PasswordController {
	
	@Autowired
	private PasswordServices passwordservices;
	
	@PostMapping("/forgotpassword")
	public ResponseEntity<?> forgotPassword(@RequestParam String email)
	{
		Response response;
		String value=passwordservices.forgotPassword(email);
			if(value.equals("found"))
			{
				response=new Response();
				response.setStatusCode(356);
				response.setStatusMessage("Reset-Mail Send To Your Eamil Address");
				return new ResponseEntity<Response>(response, HttpStatus.FOUND);
			}
			
			response=new Response();
			response.setStatusCode(367);
			response.setStatusMessage("Their is No Such User,Invalid Email Address");
			return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);	
	}
	
/*	@PostMapping("/resetpassword")
	public ResponseEntity<?> resetPassword(@RequestParam String email)
	{
		return null;
	}*/
	
}
