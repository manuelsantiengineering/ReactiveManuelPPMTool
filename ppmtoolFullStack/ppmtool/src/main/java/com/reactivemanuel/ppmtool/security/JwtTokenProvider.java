package com.reactivemanuel.ppmtool.security;


import org.springframework.security.core.Authentication;

import com.reactivemanuel.ppmtool.domain.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.reactivemanuel.ppmtool.security.SecurityConstants.EXPIRATION_TIME;
import static com.reactivemanuel.ppmtool.security.SecurityConstants.SECRET_KEY;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenProvider {

	// Generate the token
	public String generateToken(Authentication authentication) {
		User user = (User)authentication.getPrincipal();
		
		Date now = new Date(System.currentTimeMillis());		
		Date expireDate = new Date(now.getTime()+EXPIRATION_TIME);
		
		String userId = Long.toString(user.getId());
		
		Map<String,Object> claims = new HashMap<>();
		claims.put("id",Long.toString(user.getId()));
		claims.put("username",user.getUsername());
		claims.put("firstName",user.getFirstName());
		// Add roles in here if necessary
		// ie.: Administrator, Software Developer, Customer,...
		
		return Jwts.builder()
				.setSubject(userId)
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
	}
	
	// Validate the token
	// Get user id from token
	
}
