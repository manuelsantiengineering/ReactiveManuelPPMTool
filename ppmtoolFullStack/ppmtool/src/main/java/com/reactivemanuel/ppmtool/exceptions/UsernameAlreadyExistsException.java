package com.reactivemanuel.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = -1618331015840763514L;

	public UsernameAlreadyExistsException(String message) {
		super(message);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
