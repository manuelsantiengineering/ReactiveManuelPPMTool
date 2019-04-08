package com.reactivemanuel.ppmtool.security;

public class SecurityConstants {

	public static final String 	SECRET_KEY 			= "SecretKeyToGenerateJWTs";
	public static final String 	TOKEN_PREFIX 		= "Bearer ";
	public static final String 	HEADER_STRING 		= "Authorization";
	
	public static final long 	EXPIRATION_TIME		= 360_000; //About 3 minutes
//	public static final long 	EXPIRATION_TIME		= 90_900_000;
	
	public static final String 	SIGN_UP_URLS 		= "/api/users/**";
	public static final String 	SWAGGER_API 		= "/swagger**";
	public static final String 	SWAGGER_API_DOCS 	= "/v2/api-docs**";
//	public static final String 	H2_URL 				= "h2-console/**";

}
