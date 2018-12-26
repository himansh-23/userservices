package com.api.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import com.api.user.utils.UserToken;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class FundooapiApplication {

	public static void main(String[] args) {
		
	//	System.out.println(UserToken.generateToken(5));
		SpringApplication.run(FundooapiApplication.class, args);
	}

}

