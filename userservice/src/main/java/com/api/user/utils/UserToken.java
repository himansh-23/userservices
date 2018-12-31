package com.api.user.utils;

import java.io.UnsupportedEncodingException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

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
	
	public static long tokenVerify(String token)
	{
		long userid=-1;
		try {
			Verification verification=JWT.require(Algorithm.HMAC256(UserToken.TOKEN_SECRET));
			JWTVerifier jwtverifier=verification.build();
			DecodedJWT decodedjwt=jwtverifier.verify(token);
			Claim claim=decodedjwt.getClaim("ID");
			userid=claim.asLong();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return userid;
		
	}

}
