package com.api.user.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.user.entity.User;
import com.api.user.exception.UserException;
import com.api.user.repository.UserRepository;
import com.api.user.utils.UserToken;

@Service
public class PasswordServicesImpl implements PasswordServices {
	
	@Autowired
	private UserRepository userrepositoty;
	
	@Override
	public User forgotPassword(String email) throws UserException
	{
		Optional<User> useravailable=userrepositoty.findByEmail(email);
		
		if(useravailable.isPresent())
		{
			return useravailable.get();
		}
		else
		{
			throw new UserException(100,"Email Address not Found");
		}
	}
	
	public User passwordReset(String token) throws Exception
	{
		long userid=UserToken.tokenVerify(token);
		return userrepositoty.findById(userid).get();
		//String registertoken=UserToken.generateToken(userid);
	}
	
	
}
