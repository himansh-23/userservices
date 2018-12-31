package com.api.user.services;

import com.api.user.entity.User;

public interface PasswordServices {

	User forgotPassword(String email)throws Exception;
	User passwordReset(String token) throws Exception;
}
