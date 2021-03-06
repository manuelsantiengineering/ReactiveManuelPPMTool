package com.reactivemanuel.ppmtool.web;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import com.reactivemanuel.ppmtool.domain.Project;
//import com.reactivemanuel.ppmtool.domain.Project;
import com.reactivemanuel.ppmtool.domain.ProjectTask;
import com.reactivemanuel.ppmtool.services.ProjectTaskService;
import com.reactivemanuel.ppmtool.services.ValidationErrorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
@Api(value = "BacklogController", description = "Endpoints to manage project tasks for a specific project.")
public class BacklogController {
	
	@Autowired
	ProjectTaskService projectTaskService;
	
	@Autowired
	private ValidationErrorService 	validationErrorService;
	
	
	@ApiOperation(
    		httpMethod = "POST",
    		value = "Create a new Project Task", 
    		notes = "This endpoint uses a post request to create a new project task inside the project with a specific project identifier. The user has to be logged in and pass a JWT in the header in order to make the request."
    					+" The request body must include the new project task information."
    					+" Returns a json with the information of the new project task.",
			nickname="createNewProjectTask",
			response = ProjectTask.class
    				)
    @ApiResponses(value={
        @ApiResponse(code = 400, message = "Missing one of the required parameters or trying to use an existing Project Identifier."),
        @ApiResponse(code = 401, message = "Unauthorized.")
    })
    @RequestMapping(value = "/{backlog_id}", method= RequestMethod.POST, produces = "application/json")	
	public ResponseEntity<?> createNewProjectTask(@Valid @RequestBody ProjectTask projectTask, 
											BindingResult result, @PathVariable String backlog_id, 
											Principal principal){

		ResponseEntity<?> errorMap = validationErrorService.mapValidationErrorService(result);	
		if(errorMap!=null)	{	return errorMap;	}
		ProjectTask projectTaskReturn = projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());
		return new ResponseEntity<ProjectTask>(projectTaskReturn, HttpStatus.CREATED);		
	}
	
	@ApiOperation(
    		httpMethod = "GET",
    		value = "Get a list of the project tasks for the project with project identifier.", 
    		notes = "This endpoint uses a get request to get all of the project tasks inside the project with a specific project identifier."
    					+" The user has to be logged in and pass a JWT in the header in order to make the request.",
			nickname="getProjectBacklog",
			response = List.class
    				)
	@ApiResponses({
        @ApiResponse(code = 400, message = "Invalid Project Identifier")
    })
	@RequestMapping(value = "/{backlog_id}", method= RequestMethod.GET, produces = "application/json")	
//	@GetMapping("/{backlog_id}")
	public List<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principal){		
		return projectTaskService.findBacklogById(backlog_id, principal.getName());		
	}
		
	@ApiOperation(
    		httpMethod = "GET",
    		value = "Get a specific project task for the project with project identifier.", 
    		notes = "This endpoint uses a get request to get a specific project task with the project task id"
    					+" inside of the project with a specific project identifier."
    					+" The user has to be logged in and pass a JWT in the header in order to make the request.",
			nickname="getProjectTask",
			response = ProjectTask.class
    				)
	@ApiResponses({
        @ApiResponse(code = 400, message = "Invalid Project Identifier")
    })
	@RequestMapping(value = "/{backlog_id}/{projectTask_id}", method= RequestMethod.GET, produces = "application/json")	
	public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String projectTask_id,
											Principal principal){	
		
//		ResponseEntity<?> errorMap = validationErrorService.mapValidationErrorService(result);	
//		if(errorMap!=null)	{	return errorMap;	}
		ProjectTask projectTask = projectTaskService.findProjectTaskByProjectSequence(backlog_id, projectTask_id,
																				principal.getName());
		
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);		

	}
	
	@ApiOperation(
    		httpMethod = "PATCH",
    		value = "Updates a specific project task for the project with project identifier.", 
    		notes = "This endpoint uses a patch request to update a specific project task with the project task id"
    					+" inside of the project with a specific project identifier."
    					+" The user has to be logged in and pass a JWT in the header in order to make the request.",
			nickname="updateProjectTask",
			response = ProjectTask.class
    				)
	@ApiResponses({
        @ApiResponse(code = 400, message = "Invalid Project Identifier")
    })
	@RequestMapping(value = "/{backlog_id}/{projectTask_id}", method= RequestMethod.PATCH, produces = "application/json")	
//	@PatchMapping("/{backlog_id}/{projectTask_id}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, 
											BindingResult result, @PathVariable String backlog_id, 
											@PathVariable String projectTask_id, Principal principal){	
		
		ResponseEntity<?> errorMap = validationErrorService.mapValidationErrorService(result);	
		if(errorMap!=null)	{	return errorMap;	}
		ProjectTask updatedProjectTask = projectTaskService.updateByProjectSequence(projectTask, backlog_id, projectTask_id,
																				principal.getName());
		return new ResponseEntity<ProjectTask>(updatedProjectTask, HttpStatus.OK);		

	}
	
	@ApiOperation(
    		httpMethod = "DELETE",
    		value = "Deletes a specific project task for the project with project identifier.", 
    		notes = "This endpoint uses a delete request to remove a specific project task with the project task id"
    					+" inside of the project with a specific project identifier."
    					+" The user has to be logged in and pass a JWT in the header in order to make the request.",
			nickname="deleteProjectTask",
			response = ProjectTask.class
    				)
	@ApiResponses({
        @ApiResponse(code = 400, message = "Invalid Project Identifier")
    })
	@RequestMapping(value = "/{backlog_id}/{projectTask_id}", method= RequestMethod.DELETE, produces = "application/json")	
//	@DeleteMapping("/{backlog_id}/{projectTask_id}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String projectTask_id,
											Principal principal){
		
		ProjectTask projectTask = projectTaskService.deleteProjectTaskByProjectSequence(backlog_id.toUpperCase(), 
																				projectTask_id.toUpperCase(),
																				principal.getName());
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK); 
	}
	
}
