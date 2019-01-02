package com.api.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.api.user.entity.LoginUser;
import com.api.user.entity.User;
import com.api.user.exception.UserException;
import com.api.user.response.FeildErrorResponse;
import com.api.user.response.Response;
import com.api.user.services.UserServices;
import com.api.user.utils.EmailUtil;
import com.api.user.utils.UserToken;

import ch.qos.logback.classic.Logger;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserServices userServices;
	/**
	 * @param user
	 * @param bindingResult
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/register")
	public ResponseEntity<Response> register(@Valid @RequestBody User user, BindingResult bindingResult, HttpServletRequest request) throws UserException
	{
		Response response;
		if(bindingResult.hasErrors()) {
		/*	response = new FeildErrorResponse(bindingResult.getFieldErrors());
			response.setStatusCode(-100);
			response.setStatusMessage("invalid Data");*/
			throw new UserException(100, "Data Not Matching Eligiable Criteria");
		}
		user =userServices.register(user);
//		EmailUtil.sendEmail("prajapat.himanshu@gmail.com", "Successfully Registered", "Click On Below link To Verify your Email Address\n"+"http://localhost:8080/api/user/verifyemail?token="+registertoken);
		EmailUtil.sendEmail(user.getEmail(),"Successful", getBody(request, user));
		
		response = new Response();
		response.setStatusCode(200);
		response.setStatusMessage("User Register Sucessfully with id: "+user.getId()+"Please Verify Your EmailId");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	/**
	 * 
	 * @param loginuser
	 * @param bindingResult
	 * @return ResponseEntity<Response>
	 * @throws Exception
	 */
	@PostMapping("/login")
	public ResponseEntity<Response> login( @RequestBody LoginUser loginuser, BindingResult bindingResult,HttpServletRequest h) throws UserException
	{	
		Response response;
		String token;
		try {
		 token =userServices.login(loginuser);
		}catch(Exception e)
		{
			throw new UserException(100,"Login Failed");
		}
		response = new Response();
		response.setStatusCode(166);
		response.setStatusMessage("Sucessfully logged in");
		HttpHeaders headers = new HttpHeaders();
		headers.add("jwtToken", token);
		return new ResponseEntity<>(response, headers, HttpStatus.FOUND); 
	}
	/**
	 * 
	 * @param token
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/verifyemail/{token}")
	public void verifyEmail(@PathVariable String token, HttpServletResponse response) throws UserException {
		
		try{
			userServices.userVerify(token);
		response.sendRedirect("https://www.google.com");
		}
		catch(Exception exception)
		{
			throw new UserException(100,"Invalid Verification Link");
		}
	}
	/**
	 * @Purpose It return Body of HttpRequest with token.
	 * @param req
	 * @param user
	 * @return String
	 * @throws Exception
	 */
	private String getBody(HttpServletRequest req, User user)throws UserException {
//		user=userServices.register(user);
		return (req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()+"api/user/verifyemail"+UserToken.generateToken(user.getId()));
	}
}
