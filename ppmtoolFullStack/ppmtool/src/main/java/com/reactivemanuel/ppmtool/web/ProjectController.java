package com.reactivemanuel.ppmtool.web;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
//import io.swagger.annotations.Example;
//import io.swagger.annotations.ExampleProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reactivemanuel.ppmtool.domain.Project;
import com.reactivemanuel.ppmtool.services.ValidationErrorService;
import com.reactivemanuel.ppmtool.services.ProjectService;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
@Api(value = "ProjectController", description = "Endpoints to manage projects inside the PPM Tool.")
public class ProjectController {
	
	@Autowired
	private ProjectService			projectService;	
	@Autowired
	private ValidationErrorService 	validationErrorService;

	@ApiOperation(
    		httpMethod = "POST",
    		value = "Create a new Project", 
    		notes = "This endpoint uses a post request to create a new project. The user has to be logged in and pass a JWT in the header in order to make the request."
    				+ "The request body must include the new project information. Returns a json with the information of the new project.",
			nickname="createNewProject",
			response = Project.class
    				)
    @ApiResponses(value={
        @ApiResponse(code = 400, message = "Missing one of the required parameters or trying to use an existing Project Identifier."),
        @ApiResponse(code = 401, message = "Unauthorized.")
    })
    @RequestMapping(value = "", method= RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, 
											BindingResult result, Principal principal){		
		ResponseEntity<?> errorMap = validationErrorService.mapValidationErrorService(result);	
		if(errorMap!=null)	{	return errorMap;	}
		
		projectService.saveOrUpdateProject(project, principal.getName());
		return new ResponseEntity<Project>(project, HttpStatus.CREATED);
		
	}	
	
	@ApiOperation(
    		httpMethod = "GET",
    		value = "Get a list of the user projects.", 
    		notes = "This endpoint uses a get request to get all of the user projects. Returns a list with all of the projects created by the logged user."
    					+" The user has to be logged in and pass a JWT in the header in order to make the request.",
			nickname="getAllProjects",
			response = List.class
    				)
	@ApiResponses({
        @ApiResponse(code = 400, message = "Invalid Project Identifier")
    })
	@RequestMapping(value = "/all", method= RequestMethod.GET, produces = "application/json")
	public List<Project> getAllProjects(Principal principal){
		return projectService.findAllProjects(principal.getName());		
	}
	
	
	@ApiOperation(
    		httpMethod = "GET",
    		value = "Find project by Project Identifier.", 
    		notes = "This endpoint uses a get request to get the project with the specified project identifier."
    					+" The user has to be logged in and pass a JWT in the header in order to make the request.",
			nickname="getProjectByIdentifier",
			response = Project.class
    				)
	@ApiResponses({
        @ApiResponse(code = 400, message = "Invalid Project Identifier")
    })
    @RequestMapping(value = "/{projectIdentifier}", method= RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectIdentifier, Principal principal){
		Project project = projectService.findProjectByIdentifier(projectIdentifier.toUpperCase(), principal.getName());
		return new ResponseEntity<Project>(project, HttpStatus.OK); 
	}
	
	@ApiOperation(
    		httpMethod = "DELETE",
    		value = "Deletes a project by Project Identifier.", 
    		notes = "This endpoint uses a delete request to delete the project with the specified project identifier."
    					+" The user has to be logged in and pass a JWT in the header in order to make the request.",
			nickname="deleteProjectByIdentifier",
			response = Project.class
    				)
	@ApiResponses({
        @ApiResponse(code = 400, message = "Invalid Project Identifier")
    })
    @RequestMapping(value = "/{projectIdentifier}", method= RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteProjectByIdentifier(@PathVariable String projectIdentifier, Principal principal){
		Project project = projectService.deleteProjectByIdentifier(projectIdentifier.toUpperCase(), principal.getName());
		return new ResponseEntity<Project>(project, HttpStatus.OK); 
	}
	
}
