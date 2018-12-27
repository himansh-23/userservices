package com.api.user.utils;

import java.io.UnsupportedEncodingException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

public class UserToken {
	
	public static String TOKEN_SECRET="gh2we43jue";
	public static String generateToken(long id)
	{
		try {
			Algorithm algorithm= Algorithm.HMAC256(TOKEN_SECRET);
			String token=JWT.create()
							.withClaim("ID", id)
							.sign(algorithm);
			
			return token;		
		}
		catch(UnsupportedEncodingException exception)
		{
			exception.printStackTrace();
		}
		catch (JWTCreationException exception) 
		{
			exception.printStackTrace();
         }
    return null;
	}

}
