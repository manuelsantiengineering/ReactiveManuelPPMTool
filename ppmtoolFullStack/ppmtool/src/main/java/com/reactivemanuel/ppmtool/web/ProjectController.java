package com.reactivemanuel.ppmtool.web;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
        @ApiResponse(code = 201, message = "Success creating the new Project", response=Project.class),
        @ApiResponse(code = 400, message = "Missing one of the required parameters or trying to use an existing Project Identifier."),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 500, message = "Unexpected error.")
    })
    @RequestMapping(value = "", method= RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, 
											BindingResult result, Principal principal){
		
		ResponseEntity<?> errorMap = validationErrorService.mapValidationErrorService(result);	
		if(errorMap!=null)	{	return errorMap;	}
		
		projectService.saveOrUpdateProject(project, principal.getName());
		return new ResponseEntity<Project>(project, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/all")
	public List<Project> getAllProjects(Principal principal){
		return projectService.findAllProjects(principal.getName());		
	}
	
	
//	@ApiOperation(
//    		httpMethod = "GET",
//    		value = "Create a new Project", 
//    		notes = "This endpoint uses a post request to create a new project. The user has to be logged in and pass a JWT in the header in order to make the request."
//    				+ "The request body must include the new project information. Returns a json with the information of the new project.",
//			nickname="createNewProject",
//			response = Project.class
//    				)
//	@ApiResponses({
//        @ApiResponse(code = 200, message = "Success!"),
//        @ApiResponse(code = 400, message = "The identifier does not exists."),
//        @ApiResponse(code = 401, message = "Unauthorized",
//        				examples = @Example(value = { 
//        								@ExampleProperty(mediaType = "Example json", value = "{\"username\": \"Invalid Username\",\"password\": \"Invalid Password\"}" ),
//        								@ExampleProperty(mediaType = "Example string", value = "Access Json Web Token (JWT) is missing or invalid." ) 
//        							})),
//        @ApiResponse(code = 500, message = "Unexpected error.")
//    })
    @GetMapping("/{projectId}")
//    @RequestMapping(value = "/{projectId}", method= RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectIdentifier, Principal principal){
		Project project = projectService.findProjectByIdentifier(projectIdentifier.toUpperCase(), principal.getName());
		return new ResponseEntity<Project>(project, HttpStatus.OK); 
	}
	
	@DeleteMapping("/{projectIdentifier}")
	public ResponseEntity<?> deleteProjectByIdentifier(@PathVariable String projectIdentifier, Principal principal){
		Project project = projectService.deleteProjectByIdentifier(projectIdentifier.toUpperCase(), principal.getName());
		return new ResponseEntity<Project>(project, HttpStatus.OK); 
	}
	
}
