package com.api.user.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Override
	public User register(User user) {
		
		user.setPassword(passwordencoder.encode(user.getPassword()));
		Optional<User> useravailable=userrepositoty.findByEmail(user.getEmail());
		if(useravailable.isPresent())
		{
			return null;
		}
		
		return userrepositoty.save(user);
	}
	
	public String login(LoginUser loginuser)
	{
		Optional<User> useravailable=userrepositoty.findByEmail(loginuser.getEmail());	
		if(!useravailable.isPresent())
		{
			return null;
		}
		boolean passwordmatch=passwordencoder.matches(loginuser.getPassword(),useravailable.get().getPassword());
		if(passwordmatch)
		{
			return UserToken.generateToken(useravailable.get().getId());
		}
		
		return null;
		
	}
	
	

}
