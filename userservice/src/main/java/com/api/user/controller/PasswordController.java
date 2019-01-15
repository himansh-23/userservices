package com.api.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.user.dto.LoginDTO;
import com.api.user.entity.User;
import com.api.user.repository.UserRepository;
import com.api.user.response.Response;
import com.api.user.services.PasswordServices;
import com.api.user.utils.EmailUtil;
import com.api.user.utils.UserToken;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("http://localhost:4200")
public class PasswordController {
	
	@Autowired
	private PasswordServices passwordservices;
	
	@Autowired
	private UserRepository userrepositoty;
	
	@Autowired
	private PasswordEncoder passwordencoder;
	
	static Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@GetMapping("/forgotpassword")
	public ResponseEntity<?> forgotPassword(@RequestParam String email,HttpServletRequest request) throws Exception
	{
		logger.info("Password Recovery");
			Response response;
			User user=passwordservices.forgotPassword(email);
			EmailUtil.sendEmail(email, "Password Reset", this.getBody(request, user,"resetpassword"));
			response=new Response();
			response.setStatusCode(356);
			response.setStatusMessage("Reset-Mail Send To Your Eamil Address");
//			String registertoken=UserToken.generateToken(user.getId());
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	/**
	 * Check The Password Reset Link
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/resetpassword/{token}") 
	public ResponseEntity<?> resetPassword(@PathVariable("token") String token,HttpServletRequest request) throws Exception
	{	
		logger.info("password reset");

			User user=passwordservices.passwordReset(token);
			EmailUtil.sendEmail(user.getEmail(), "ChangePassword", this.getBody(request, user,"reset"));
			Response response=new Response();
			response.setStatusCode(200);
			response.setStatusMessage("Redirect To New Password Set Page");	
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	/**
	 * Redirect To Password Reset Page
	 * @param token
	 * @param loginuser
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/resetpage/{token}" ,method=RequestMethod.POST)
	public ResponseEntity<?> resetPage(@PathVariable("token") String token,@RequestBody LoginDTO loginuser) throws Exception
	{
		logger.info("Password reset page");
			long userid=UserToken.tokenVerify(token);
			Response response=new Response();
			User user=userrepositoty.findById(userid).get();
			user.setPassword(passwordencoder.encode(loginuser.getPassword()));
			userrepositoty.save(user);
			response.setStatusCode(200);
			response.setStatusMessage("Password Set Successfully");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	private String getBody(HttpServletRequest req, User user,String link)throws Exception {
		return "http://localhost:4200/"+link+"/"+UserToken.generateToken(user.getId());
	}
}
