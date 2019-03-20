package com.reactivemanuel.ppmtool.security;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.reactivemanuel.ppmtool.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import static com.reactivemanuel.ppmtool.security.SecurityConstants.EXPIRATION_TIME;
import static com.reactivemanuel.ppmtool.security.SecurityConstants.SECRET_KEY;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
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
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return true;
		}catch(SignatureException ex) {
			System.out.println("Invalid JWT Signature");			
		}catch(MalformedJwtException ex) {
			System.out.println("Invalid JWT Token");			
		}catch(ExpiredJwtException ex) {
			System.out.println("Expired JWT Token");			
		}catch(UnsupportedJwtException ex) {
			System.out.println("Unsuported JWT Token");			
		}catch(IllegalArgumentException ex) {
			System.out.println("JWT claims string is empty");			
		}
		return false;
	}
	
	// Get user id from token
	public Long getUserIdFromJWT(String token) {		
		Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
		String userId = (String)claims.get("id");
		return Long.parseLong(userId);
	}
}
