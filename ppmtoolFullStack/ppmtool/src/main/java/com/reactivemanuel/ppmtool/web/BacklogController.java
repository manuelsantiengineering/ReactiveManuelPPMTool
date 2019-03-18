package com.reactivemanuel.ppmtool.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactivemanuel.ppmtool.domain.Project;
import com.reactivemanuel.ppmtool.domain.ProjectTask;
import com.reactivemanuel.ppmtool.services.ProjectTaskService;
import com.reactivemanuel.ppmtool.services.ValidationErrorService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {
	
	@Autowired
	ProjectTaskService projectTaskService;
	
	@Autowired
	private ValidationErrorService 	validationErrorService;
		
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> createNewProjectTask(@Valid @RequestBody ProjectTask projectTask, 
											BindingResult result, @PathVariable String backlog_id){
		
		ResponseEntity<?> errorMap = validationErrorService.mapValidationErrorService(result);	
		if(errorMap!=null)	{	return errorMap;	}
		ProjectTask projectTaskReturn = projectTaskService.addProjectTask(backlog_id, projectTask);
		return new ResponseEntity<ProjectTask>(projectTaskReturn, HttpStatus.CREATED);		
	}
	
	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id){		
		return projectTaskService.findBacklogById(backlog_id);		
	}
	
	@GetMapping("/{backlog_id}/{projectTask_id}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String projectTask_id){			
//		ResponseEntity<?> errorMap = validationErrorService.mapValidationErrorService(result);	
//		if(errorMap!=null)	{	return errorMap;	}
		ProjectTask projectTask = projectTaskService.findProjectTaskByProjectSequence(backlog_id, projectTask_id);
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);		

	}
	
	@PatchMapping("/{backlog_id}/{projectTask_id}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, 
											BindingResult result, @PathVariable String backlog_id, 
											@PathVariable String projectTask_id){	
		
		ResponseEntity<?> errorMap = validationErrorService.mapValidationErrorService(result);	
		if(errorMap!=null)	{	return errorMap;	}
		ProjectTask updatedProjectTask = projectTaskService.updateByProjectSequence(projectTask, backlog_id, projectTask_id);
		return new ResponseEntity<ProjectTask>(updatedProjectTask, HttpStatus.OK);		

	}
	
	@DeleteMapping("/{backlog_id}/{projectTask_id}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String projectTask_id){	
		ProjectTask projectTask = projectTaskService.deleteProjectTaskByProjectSequence(backlog_id.toUpperCase(), projectTask_id.toUpperCase());
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK); 
	}
	
}
