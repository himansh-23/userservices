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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.user.entity.LoginUser;
import com.api.user.entity.User;
import com.api.user.response.FeildErrorResponse;
import com.api.user.response.Response;
import com.api.user.services.MailServices;
import com.api.user.services.UserServices;
import com.api.user.utils.UserToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

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
		
		String registertoken=UserToken.generateToken(user.getId());
		mailservices.mailSend("prajapat.himanshu@gmail.com", "Successfully Registered", "Click On Below link To Verify your Email Address\n"+"https://localhost:8080/api/user/verifyemail?verify"+registertoken);
		
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response> login( @RequestBody LoginUser loginuser, BindingResult bindingResult) throws Exception
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
	
	@PostMapping("/verifyemail")
	public ResponseEntity<Response> verifyEmail(@RequestParam String verifyemail )
	{
		try {
				Verification verification=JWT.require(Algorithm.HMAC256(UserToken.TOKEN_SECRET));
				JWTVerifier jwtverifier=verification.build();
				DecodedJWT decodedjwt=jwtverifier.verify(verifyemail);
				Claim claim=decodedjwt.getClaim("ID");
				int userid=claim.asInt();
				
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		return null;	
	}
	
	//create method for jwt token verification agter register
	
}
