package com.api.user.services;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.api.user.dto.LoginDTO;
import com.api.user.dto.UserDTO;
import com.api.user.entity.User;
import com.api.user.exception.UserException;
import com.api.user.repository.UserRepository;
import com.api.user.utils.UserToken;
import org.modelmapper.ModelMapper;
@Service
public class UserServicesImpl implements UserServices {

	@Autowired
	private UserRepository userRepositoty;
	@Autowired
	private PasswordEncoder passwordencoder;
	@Autowired
	private ModelMapper modelMapper;
	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@Override
	public User register(UserDTO userDTO) throws UserException {
		
		Optional<User> useravailable=userRepositoty.findByEmail(userDTO.getEmail());
		if(useravailable.isPresent())
		{
			throw new UserException(100,"Duplicate user found");
		}
		User user=modelMapper.map(userDTO, User.class);
		user.setPassword(passwordencoder.encode(user.getPassword()));
		return userRepositoty.save(user);
	}
	
	/**
	 * 
	 * @param loginuser
	 * @return
	 * @throws Exception
	 */
	@Override
	public String login(LoginDTO loginuser) throws UserException{
	
		return userRepositoty.findByEmail(loginuser.getEmail())
							 .map(fromDBUser-> {
								try {
									return this.validUser(fromDBUser, loginuser.getPassword());
								} catch (UserException e) {
									new UserException(100,"Please Verify Your mail"); 
									e.printStackTrace();
								}
								return null;
							})
							 .orElseThrow(()-> new UserException(100,"Not valid User"));
	}
	private String validUser(User fromDBUser, String password) throws UserException{
		boolean isValid =passwordencoder.matches(password, fromDBUser.getPassword());
		if(isValid){ 
			return UserToken.generateToken(fromDBUser.getId());
		}
		throw new UserException(100,"Not valid User");
	}
	/**
	 * 
	 * @param token
	 * @throws UnsupportedEncodingException 
	 * @throws IllegalArgumentException 
	 * @throws Exception
	 */
	@Override
	public void userVerify(String token) throws Exception{
		long userId = UserToken.tokenVerify(token);
		userRepositoty.findById(userId).map(this::verify).orElseThrow(()-> new UserException(100,"Not valid User"));
	}
	private User verify(User user) {
		user.setIsverification(true);
		return userRepositoty.save(user);
	}
}
