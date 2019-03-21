package com.reactivemanuel.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactivemanuel.ppmtool.domain.Backlog;
import com.reactivemanuel.ppmtool.domain.ProjectTask;
import com.reactivemanuel.ppmtool.exceptions.ProjectIdException;
import com.reactivemanuel.ppmtool.exceptions.ProjectNotFoundException;
import com.reactivemanuel.ppmtool.repositories.BacklogRepository;
import com.reactivemanuel.ppmtool.repositories.ProjectRepository;
import com.reactivemanuel.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	
	@Autowired
	private ProjectRepository projectRepository;	
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	private ProjectService projectService;
	
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {		
		// We want all the project tasks to be added to a specific project, project not null
		// We need a backlog object in order to have a task.
		// We want to set an initial priority level.
		// We want to set an initial status when it is null.			
		Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
		projectTask.setBacklog(backlog);
		
		Integer backlogProjectTaskSequence = backlog.getProjectTaskSequence();
		backlogProjectTaskSequence++;
		backlog.setProjectTaskSequence(backlogProjectTaskSequence);		

		projectTask.setProjectSequence(projectIdentifier+"-"+backlogProjectTaskSequence);
		projectTask.setProjectIdentifier(projectIdentifier);
		
		if(projectTask.getPriority()==null || projectTask.getPriority()==0) {
			projectTask.setPriority(3);
		}
		if(projectTask.getStatus()==null || projectTask.getStatus()=="") {
			projectTask.setStatus("TO_DO");
		}
		return projectTaskRepository.save(projectTask);		
	}


	public Iterable<ProjectTask> findBacklogById(String projectIdentifier, String username) {		
		projectService.findProjectByIdentifier(projectIdentifier, username);		
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier);
	}
	
	public ProjectTask findProjectTaskByProjectSequence(String projectIdentifier, String projectSequence) {
		// Need to take the backlog Id and the project sequence to make sure there is no ambiguity.
		// Make sure we are searching on an existing backlog.
		if(backlogRepository.findByProjectIdentifier(projectIdentifier)==null) {
			throw new ProjectNotFoundException("Project Identifier '" + projectIdentifier + "' does not exists.");
		}
		// Make sure that our task exists
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(projectSequence);
		if(projectTask==null) {
			throw new ProjectNotFoundException("Project Task '" + projectSequence + "' does not exists.");
		}
		// Make sure that the backlog project id in the path corresponds to the right project
		if(!projectTask.getProjectIdentifier().equals(projectIdentifier)) {
			throw new ProjectNotFoundException("Project Task '" + projectSequence + "' does not exists in project '" + projectIdentifier + "'.");
		}
		
		return projectTask;
	}
	
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String projectIdentifier, String projectSequence) {
		ProjectTask projectTask = findProjectTaskByProjectSequence(projectIdentifier, projectSequence);		
		if(!updatedTask.getProjectSequence().equals(projectSequence)) {
			throw new ProjectNotFoundException("Project Task '" + projectSequence + 
											"' does not match URL project task '" + updatedTask.getProjectSequence() + "'.");
		}
		// Make sure the id matches
		if(updatedTask.getId() != projectTask.getId()) {
			throw new ProjectNotFoundException("Project Task Id. '" + projectTask.getId() +
											"' does not match URL project task Id. '" + updatedTask.getId() + "'.");
		}
		// Replace and save with the updated task		
		return projectTaskRepository.save(updatedTask);
	}

	public ProjectTask deleteProjectTaskByProjectSequence(String projectIdentifier, String projectSequence) {
		ProjectTask projectTask = findProjectTaskByProjectSequence(projectIdentifier, projectSequence);
		projectTaskRepository.delete(projectTask);
		return projectTask;
	}
	
}
