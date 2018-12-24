package com.api.user.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.user.entity.User;
import com.api.user.repository.UserRepository;

@Service
public class UserServicesImpl implements UserServices {

	@Autowired
	private UserRepository userrepositoty;
	@Autowired
	private PasswordEncoder passwordencoder;
	@Override
	public User register(User user) {
		
		user.setPassword(passwordencoder.encode(user.getPassword()));
		if(userrepositoty.findByEmail(user.getEmail())!=null)
		{
			return new User();
		}
		return userrepositoty.save(user);
	}
	
	

}
