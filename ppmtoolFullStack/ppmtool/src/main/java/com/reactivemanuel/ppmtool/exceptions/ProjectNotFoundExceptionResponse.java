package com.reactivemanuel.ppmtool.exceptions;

public class ProjectNotFoundExceptionResponse {
	public String projectNotFound;

	public ProjectNotFoundExceptionResponse(String projectNotFound) {
		super();
		this.projectNotFound = projectNotFound;
	}

	public String getProjectNotFound() {
		return this.projectNotFound;
	}

	public void setProjectNotFound(String projectNotFound) {
		this.projectNotFound = projectNotFound;
	}	
	
}
