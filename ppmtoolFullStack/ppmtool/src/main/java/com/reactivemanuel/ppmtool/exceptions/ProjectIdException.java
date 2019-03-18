package com.reactivemanuel.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectIdException extends RuntimeException{
	private static final long serialVersionUID = -4968396673369653835L;

	public ProjectIdException(String message){
		super(message);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

	
}
