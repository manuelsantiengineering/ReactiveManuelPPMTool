package com.reactivemanuel.ppmtool.payload;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class LoginRequest {
	
	@NotBlank(message="Username cannot be blank.")
	@ApiModelProperty(required=true, example="username@email.com",position=1)
	private String username;
	@NotBlank(message="Password cannot be blank.")
	@ApiModelProperty(required=true, example="password",position=2)
	private String password;
	
	public String getUsername() {
		return username;
	}
	
//	public void setUsername(String username) {
//		this.username = username;
//	}
	
	public String getPassword() {
		return password;
	}
	
//	public void setPassword(String password) {
//		this.password = password;
//	}
	
	
	
	
}
