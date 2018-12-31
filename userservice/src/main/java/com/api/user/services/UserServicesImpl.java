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
	private UserRepository userRepositoty;
	@Autowired
	private PasswordEncoder passwordencoder;
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@Override
	public User register(User user) throws Exception {
		
		user.setPassword(passwordencoder.encode(user.getPassword()));
		Optional<User> useravailable=userRepositoty.findByEmail(user.getEmail());
		if(useravailable.isPresent())
		{
			throw new Exception("Duplicate user found");
		}
		return userRepositoty.save(user);
	}
	
	/**
	 * 
	 * @param loginuser
	 * @return
	 * @throws Exception
	 */
	@Override
	public String login(LoginUser loginuser) throws Exception{
		
		return userRepositoty.findByEmail(loginuser.getEmail())
							 .map(fromDBUser-> this.validUser(fromDBUser, loginuser.getPassword()))
							 .orElseThrow(()-> new Exception("Not valid User"));
	}
	private String validUser(User fromDBUser, String password){
		boolean isValid =passwordencoder.matches(password, fromDBUser.getPassword());
		String  token = null;
		if(isValid){ 
			token=UserToken.generateToken(fromDBUser.getId());
		}
		return  token;
	}
	/**
	 * 
	 * @param token
	 * @throws Exception
	 */
	@Override
	public void userVerify(String token) throws Exception {
		long userId = UserToken.tokenVerify(token);
		userRepositoty.findById(userId).map(this::verify).orElseThrow(()-> new Exception("Not valid User"));
	}
	private User verify(User user) {
		user.setIsverification(true);
		return userRepositoty.save(user);
	}
}
