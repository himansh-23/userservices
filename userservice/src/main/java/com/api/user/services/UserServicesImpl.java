package com.api.user.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.user.entity.LoginUser;
import com.api.user.entity.User;
import com.api.user.repository.UserRepository;
import com.api.user.utils.UserToken;

@Service
public class UserServicesImpl implements UserServices {

	@Autowired
	private UserRepository userrepositoty;
	@Autowired
	private PasswordEncoder passwordencoder;
	
	@Value("${mail.properties.subject}")
	private String subject;
	@Value("${mail.properties.body}")
	private String body;
	
	@Autowired
	private MailServices mailservices;
	
	@Override
	public User register(User user) {
		
		user.setPassword(passwordencoder.encode(user.getPassword()));
		Optional<User> useravailable=userrepositoty.findByEmail(user.getEmail());
		if(useravailable.isPresent())
		{
			return null;
		}
		mailservices.mailSend(user.getEmail(),subject,body);
		return userrepositoty.save(user);
	}
	
	public String login(LoginUser loginuser) throws Exception
	{
		return userrepositoty.findByEmail(loginuser.getEmail())
							 .map(fromDBUser-> this.validUser(fromDBUser, loginuser.getPassword()))
							 .orElseThrow(()-> new Exception("Not valid User"));
	}
	
	private String validUser(User fromDBUser, String password){
		boolean isValid =passwordencoder.matches(password, fromDBUser.getPassword());
		
		String  token = null;
		if(isValid) { 
			token=UserToken.generateToken(fromDBUser.getId());
		}
		return  token;
	}
	
	

}
