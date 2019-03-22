package com.reactivemanuel.ppmtool.security;

public class SecurityConstants {

	public static final String 	SECRET_KEY 		= "SecretKeyToGenerateJWTs";
	public static final String 	TOKEN_PREFIX 	= "Bearer ";
	public static final String 	HEADER_STRING 	= "Authorization";
	
	public static final long 	EXPIRATION_TIME	= 60_000; //About 30 sec
//	public static final long 	EXPIRATION_TIME	= 90_900_000;
	
	public static final String 	SIGNUP_UP_URLS 	= "/api/users/**";
	public static final String 	H2_URL 			= "h2-console/**";

}
