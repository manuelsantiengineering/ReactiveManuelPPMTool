package com.reactivemanuel.ppmtool.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.reactivemanuel.ppmtool.domain.User;
//import com.reactivemanuel.ppmtool.exceptions.InvalidLoginException;
//import com.reactivemanuel.ppmtool.exceptions.ProjectNotFoundException;

@Component
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {		
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		
		if(user.getPassword() == null || user.getPassword().contentEquals("")) {
			user.setPassword("");
			errors.rejectValue("password", "Length", "Password is required.");
		}else if(user.getPassword().length() < 6) {
			errors.rejectValue("password", "Length", "Password must be at least 6 characters.");
		}
		
		if(user.getConfirmPassword() == null || user.getConfirmPassword().contentEquals("")) {
			user.setConfirmPassword("");;
			errors.rejectValue("confirmPassword", "Length", "Confirmation password is required.");
		}else if(!user.getPassword().contentEquals(user.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "Match", "Passwords must match.");
		}		
		
	}

	
}
