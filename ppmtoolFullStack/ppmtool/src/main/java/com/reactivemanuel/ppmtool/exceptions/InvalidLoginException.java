package com.reactivemanuel.ppmtool.exceptions;

public class InvalidLoginException extends RuntimeException{

	private static final long serialVersionUID = 8227559088599925635L;

	public InvalidLoginException(String message){
		super(message);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
