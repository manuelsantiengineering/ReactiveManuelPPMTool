package com.reactivemanuel.ppmtool.exceptions;

public class ProjectNotFoundExceptionResponse {

	public String ProjectNotFound;

	public ProjectNotFoundExceptionResponse(String projectNotFound) {
		super();
		ProjectNotFound = projectNotFound;
	}

	public String getProjectNotFound() {
		return ProjectNotFound;
	}

	public void setProjectNotFound(String projectNotFound) {
		ProjectNotFound = projectNotFound;
	}	
	
}
