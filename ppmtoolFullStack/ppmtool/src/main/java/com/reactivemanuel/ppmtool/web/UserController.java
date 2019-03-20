package com.reactivemanuel.ppmtool.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactivemanuel.ppmtool.domain.User;
import com.reactivemanuel.ppmtool.payload.JWTLoginSucessResponse;
import com.reactivemanuel.ppmtool.payload.LoginRequest;
import com.reactivemanuel.ppmtool.security.JwtTokenProvider;
import com.reactivemanuel.ppmtool.services.UserService;
import com.reactivemanuel.ppmtool.services.ValidationErrorService;
import com.reactivemanuel.ppmtool.validator.UserValidator;

import static com.reactivemanuel.ppmtool.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;	
	@Autowired
	private ValidationErrorService 	validationErrorService;	
	@Autowired
	private UserValidator userValidator;	
	@Autowired
	private JwtTokenProvider tokenProvider;	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
		ResponseEntity<?> errorMap = validationErrorService.mapValidationErrorService(result);
		if(errorMap!=null) {	return errorMap;	}
		
		Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(
								loginRequest.getUsername(),
								loginRequest.getPassword()
								));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);
		
		return ResponseEntity.ok(new JWTLoginSucessResponse(true, jwt));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
		userValidator.validate(user, result);
		ResponseEntity<?> errorMap = validationErrorService.mapValidationErrorService(result);
		if(errorMap!=null) {	return errorMap;	}
		
		User newUser = userService.saveUser(user);
		
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
}
