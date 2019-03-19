package com.reactivemanuel.ppmtool.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.reactivemanuel.ppmtool.exceptions.InvalidLoginResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
							AuthenticationException authException) throws IOException, ServletException {		
		
		InvalidLoginResponse invalidResponse = new InvalidLoginResponse();
		String jsonLoginResponse = new Gson().toJson(invalidResponse);
		
		httpServletResponse.setContentType("application/json");
//		httpServletResponse.sendError(401);
		httpServletResponse.setStatus(401);
		httpServletResponse.getWriter().print(jsonLoginResponse);;
	}

	
	
}
