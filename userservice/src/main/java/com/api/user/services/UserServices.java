package com.api.user.services;

import com.api.user.dto.LoginDTO;
import com.api.user.dto.UserDTO;
import com.api.user.entity.User;
import com.api.user.exception.UserException;

public interface UserServices {
	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User register(UserDTO userDTO) throws UserException;
	/**
	 * 
	 * @param loginuser
	 * @return
	 * @throws Exception
	 */
	public String login(LoginDTO loginuser) throws Exception;
	/**
	 * 
	 * @param token
	 * @throws Exception
	 */
	public void userVerify(String token) throws Exception;
	
	public Long collabUserId(String token,String email) throws UserException;
	 
}
