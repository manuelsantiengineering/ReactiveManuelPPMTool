package com.reactivemanuel.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactivemanuel.ppmtool.domain.Backlog;
import com.reactivemanuel.ppmtool.domain.ProjectTask;
import com.reactivemanuel.ppmtool.repositories.BacklogRepository;
import com.reactivemanuel.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		// We want all the project tasks to be added to a specific project, project not null
		// We need a backlog object in order to have a task.
		// We want to set an initial priority level.
		// We want to set an initial status when it is null.
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
		projectTask.setBacklog(backlog);
		Integer backlogProjectTaskSequence = backlog.getProjectTaskSequence();
		backlogProjectTaskSequence++;
		backlog.setProjectTaskSequence(backlogProjectTaskSequence);
		// Add sequence to project task
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
	
}
