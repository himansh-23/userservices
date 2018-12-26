package com.api.user.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.user.entity.LoginUser;
import com.api.user.entity.User;
import com.api.user.response.FeildErrorResponse;
import com.api.user.response.Response;
import com.api.user.services.MailServices;
import com.api.user.services.UserServices;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserServices userservices;
	@Autowired
	MailServices mailservices;
	@PostMapping("/register")
	public ResponseEntity<Response> register(@Valid @RequestBody User user, BindingResult bindingResult)
	{
		Response response;
		if(bindingResult.hasErrors()) {
			response = new FeildErrorResponse(bindingResult.getFieldErrors());
			response.setStatusCode(-100);
			response.setStatusMessage("invalid Data");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
		user =userservices.register(user);
		if(user==null)
		{
			response=new Response();
			response.setStatusCode(-21);
			response.setStatusMessage("Email Already Exists");
			return new ResponseEntity<Response>(response,HttpStatus.ALREADY_REPORTED);
			
		}
		response = new Response();
		response.setStatusCode(100);
		response.setStatusMessage("User Register Sucessfully with id: "+user.getId());
		mailservices.mailSend(user.getEmail());
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response> login( @RequestBody LoginUser loginuser, BindingResult bindingResult)
	{	
		Response response;
		String userToken =userservices.login(loginuser);
		if(userToken==null)
		{
			response = new Response();
			response.setStatusCode(-66);
			response.setStatusMessage("User Not Found: ");
			return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND); 
		}
		response = new Response();
		response.setStatusCode(166);
		response.setStatusMessage(userToken);
		return new ResponseEntity<Response>(response, HttpStatus.FOUND); 
	}
	
	
}
