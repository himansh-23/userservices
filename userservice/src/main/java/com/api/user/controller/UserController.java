package com.api.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
import com.api.user.response.FeildErrorResponse;
import com.api.user.response.Response;
import com.api.user.services.UserServices;
import com.api.user.utils.EmailUtil;
import com.api.user.utils.UserToken;

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
	public ResponseEntity<Response> register(@Valid @RequestBody User user, BindingResult bindingResult, HttpServletRequest request) throws Exception
	{
		Response response;
		if(bindingResult.hasErrors()) {
			response = new FeildErrorResponse(bindingResult.getFieldErrors());
			response.setStatusCode(-100);
			response.setStatusMessage("invalid Data");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
		user =userServices.register(user);
		response = new Response();
		response.setStatusCode(100);
		response.setStatusMessage("User Register Sucessfully with id: "+user.getId()+"Please Verify Your EmailId");
//		EmailUtil.sendEmail("prajapat.himanshu@gmail.com", "Successfully Registered", "Click On Below link To Verify your Email Address\n"+"http://localhost:8080/api/user/verifyemail?token="+registertoken);
		EmailUtil.sendEmail(user.getEmail(),"xyf", getBody(request, user));
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
	public ResponseEntity<Response> login( @RequestBody LoginUser loginuser, BindingResult bindingResult,HttpServletRequest h) throws Exception
	{	
		Response response;
		String token =userServices.login(loginuser);
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
	public void verifyEmail(@PathVariable String token, HttpServletResponse response) throws Exception {
		userServices.userVerify(token);
		response.sendRedirect("https://www.google.com");
	}
	/**
	 * @Purpose It return Body of HttpRequest with token.
	 * @param req
	 * @param user
	 * @return String
	 * @throws Exception
	 */
	private String getBody(HttpServletRequest req, User user)throws Exception {
//		user=userServices.register(user);
		return (req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()+"api/user/verifyemail"+UserToken.generateToken(user.getId()));
	}
}
