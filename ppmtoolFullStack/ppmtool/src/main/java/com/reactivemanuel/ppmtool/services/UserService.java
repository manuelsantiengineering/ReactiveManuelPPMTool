package com.reactivemanuel.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.reactivemanuel.ppmtool.domain.User;
import com.reactivemanuel.ppmtool.exceptions.ProjectNotFoundException;
import com.reactivemanuel.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.reactivemanuel.ppmtool.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	public User saveUser(User newUser) {
		try {			
			newUser.setUsername(newUser.getUsername().toLowerCase());
			// Make sure that password and confirmPassword match
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			newUser.setConfirmPassword("");

			return userRepository.save(newUser);		
			
		}catch (Exception e) {
			// Username has to be unique
			throw new UsernameAlreadyExistsException("Username already exists.");
		}

	}
	
}
