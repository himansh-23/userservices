package com.api.user.utils;

import com.api.user.exception.UserException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

public class UserToken {
	
	private static String TOKEN_SECRET="gh2we43jue";
	public static String generateToken(long id) throws UserException
	{
		try {
			Algorithm algorithm= Algorithm.HMAC256(TOKEN_SECRET);
			String token=JWT.create()
							.withClaim("ID", id)
							.sign(algorithm);
			return token;		
		}
		catch(Exception exception)
		{
			throw new UserException(100,"Token Not Generated");
		}
	}
	
	public static long tokenVerify(String token)throws UserException	
	{
		long userid;
		try {
			Verification verification=JWT.require(Algorithm.HMAC256(UserToken.TOKEN_SECRET));
			JWTVerifier jwtverifier=verification.build();
			DecodedJWT decodedjwt=jwtverifier.verify(token);
			Claim claim=decodedjwt.getClaim("ID");
			userid=claim.asLong();	
		//	System.out.println(userid);
		}
		catch(Exception exception)
		{
			throw new UserException(100,"Token Not Verified");
		}
		
			return userid;
	}

}
