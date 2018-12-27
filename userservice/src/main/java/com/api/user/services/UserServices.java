package com.api.user.services;

import com.api.user.entity.LoginUser;
import com.api.user.entity.User;

public interface UserServices {

	public User register(User user);
	public String login(LoginUser loginuser) throws Exception;
}
