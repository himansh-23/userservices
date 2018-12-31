package com.api.user.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.user.entity.User;
import com.api.user.repository.UserRepository;

@Service
public class PasswordServicesImpl implements PasswordServices {
	
	@Autowired
	private UserRepository userrepositoty;
	
	@Override
	public User forgotPassword(String email) 
	{
		Optional<User> useravailable=userrepositoty.findByEmail(email);
		
		if(useravailable.isPresent())
		{
			return useravailable.get();
		}
		return null;
	}
	
}
