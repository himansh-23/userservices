package com.api.user.services;

import com.api.user.entity.LoginUser;
import com.api.user.entity.User;
import com.api.user.exception.UserException;

public interface UserServices {
	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User register(User user) throws UserException;
	/**
	 * 
	 * @param loginuser
	 * @return
	 * @throws Exception
	 */
	public String login(LoginUser loginuser) throws Exception;
	/**
	 * 
	 * @param token
	 * @throws Exception
	 */
	public void userVerify(String token) throws Exception;
	 
}
