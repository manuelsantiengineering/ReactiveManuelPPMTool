package com.reactivemanuel.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactivemanuel.ppmtool.domain.Project;
import com.reactivemanuel.ppmtool.exceptions.ProjectIdException;
import com.reactivemanuel.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	public Project saveOrUpdateProject(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);			
		}catch (Exception e) {
			throw new ProjectIdException("Project Identifier '" + project.getProjectIdentifier().toUpperCase() + "'already exists.");
		}

	}
	
}
