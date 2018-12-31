package com.api.user.services;

import com.api.user.entity.LoginUser;
import com.api.user.entity.User;

public interface UserServices {
	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User register(User user) throws Exception;
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
