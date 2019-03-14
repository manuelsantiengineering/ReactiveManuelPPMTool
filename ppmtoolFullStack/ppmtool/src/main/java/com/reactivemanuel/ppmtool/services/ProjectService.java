package com.reactivemanuel.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactivemanuel.ppmtool.domain.Backlog;
import com.reactivemanuel.ppmtool.domain.Project;
import com.reactivemanuel.ppmtool.exceptions.ProjectIdException;
import com.reactivemanuel.ppmtool.repositories.BacklogRepository;
import com.reactivemanuel.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;	
	@Autowired
	private BacklogRepository backlogRepository;
	
	public Project saveOrUpdateProject(Project project) {
		try {
			String newProjectIdentifier = project.getProjectIdentifier().toUpperCase();
			project.setProjectIdentifier(newProjectIdentifier);
			if(project.getId()==null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(newProjectIdentifier);
			}else {
				project.setBacklog(backlogRepository.findByProjectIdentifier(newProjectIdentifier));
			}			
			return projectRepository.save(project);			
		}catch (Exception e) {
			throw new ProjectIdException("Project Identifier '" + project.getProjectIdentifier().toUpperCase() + "'already exists.");
		}

	}
	
	public Project findProjectByIdentifier(String projectIdentifier) {
		Project project = projectRepository.findByProjectIdentifier(projectIdentifier);
		
		if(project==null) {
			throw new ProjectIdException("Project Identifier '" + projectIdentifier + "' does not exists.");
		}		
		return project;
	}
	
	public Iterable<Project> findAllProjects(){
		return projectRepository.findAll();
	}
	
	public Project deleteProjectByIdentifier(String projectIdentifier) {
		Project project = projectRepository.findByProjectIdentifier(projectIdentifier);
		
		if(project==null) {
			throw new ProjectIdException("Project Identifier '" + projectIdentifier + "' does not exists.");
		}
		projectRepository.delete(project);
		return project;
	}
	
}
