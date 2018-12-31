package com.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.api.user.entity.LoginUser;
import com.api.user.entity.User;
import com.api.user.repository.UserRepository;
import com.api.user.response.Response;
import com.api.user.services.PasswordServices;
import com.api.user.utils.EmailUtil;
import com.api.user.utils.UserToken;

@RequestMapping("/api/user")
@RestController
public class PasswordController {
	
	@Autowired
	private PasswordServices passwordservices;
	
	@Autowired
	private UserRepository userrepositoty;
	
	@Autowired
	private PasswordEncoder passwordencoder;
	
	@GetMapping("/forgotpassword")
	public ResponseEntity<?> forgotPassword(@RequestParam String email)
	{
		Response response;
		User user=passwordservices.forgotPassword(email);
		
		if(user!=null)
		{
			response=new Response();
			response.setStatusCode(356);
			response.setStatusMessage("Reset-Mail Send To Your Eamil Address");
			String registertoken=UserToken.generateToken(user.getId());
			EmailUtil.sendEmail("prajapat.himanshu@gmail.com", "Password Reset", "Click On Below link To reset Your Password\n"+"http://localhost:8080/api/user/resetpassword/"+registertoken);
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
		response=new Response();
		response.setStatusCode(367);
		response.setStatusMessage("Their is No Such User,Invalid Email Address");
		return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);	
	}
	
	@RequestMapping(value="/resetpassword/{token}")
	public ResponseEntity<?> resetPassword(@PathVariable("token") String token)
	{	
			Response response=new Response();
			long userid=UserToken.tokenVerify(token);
			if(userid>0)
			{
			String registertoken=UserToken.generateToken(userid);
			EmailUtil.sendEmail("prajapat.himanshu@gmail.com", "ChangePassword", "Click On Below link To Change Your Password\n"+"http://localhost:8080/api/user/resetpage/"+registertoken);	
			response.setStatusCode(200);
			response.setStatusMessage("Redirect To New Password Set Page");
			}
			else
			{
			response.setStatusCode(456);
			response.setStatusMessage("Invalid Password Reset Link");
		}
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/resetpage/{token}" ,method=RequestMethod.POST)
	public ResponseEntity<?> resetPage(@PathVariable("token") String token,@RequestBody LoginUser loginuser)
	{
		long userid=UserToken.tokenVerify(token);
		Response response=new Response();
		if(userid>0)
		{
			User user=userrepositoty.findById(userid).get();
			user.setPassword(passwordencoder.encode(loginuser.getPassword()));
			userrepositoty.save(user);
			response.setStatusCode(200);
			response.setStatusMessage("Password Set Successfully");
		}
		else
		{
			response.setStatusCode(288);
			response.setStatusMessage("Password Reset Link is Not Valid");
		}
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
