package com.api.user.services;

import com.api.user.entity.User;

public interface PasswordServices {

	User forgotPassword(String email);
}
