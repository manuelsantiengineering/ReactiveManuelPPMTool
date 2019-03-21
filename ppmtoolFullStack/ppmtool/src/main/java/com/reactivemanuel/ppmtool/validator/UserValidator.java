package com.reactivemanuel.ppmtool.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.reactivemanuel.ppmtool.domain.User;
import com.reactivemanuel.ppmtool.exceptions.ProjectNotFoundException;

@Component
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {		
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		if(user.getPassword() == null) {
			throw new ProjectNotFoundException("Password is required.");
		}
		if(user.getConfirmPassword() == null) {
			throw new ProjectNotFoundException("Confirmation password is required.");
		}
		if(user.getPassword().length() < 6) {
			errors.rejectValue("password", "Length", "Password must be at least 6 characters.");
		}
		if(!user.getPassword().contentEquals(user.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "Match", "Passwords must match.");
		}		
	}

	
}
