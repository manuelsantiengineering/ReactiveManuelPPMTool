package com.reactivemanuel.ppmtool.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactivemanuel.ppmtool.domain.Backlog;
import com.reactivemanuel.ppmtool.domain.Project;
import com.reactivemanuel.ppmtool.domain.User;
import com.reactivemanuel.ppmtool.exceptions.ProjectIdException;
import com.reactivemanuel.ppmtool.exceptions.ProjectNotFoundException;
import com.reactivemanuel.ppmtool.repositories.BacklogRepository;
import com.reactivemanuel.ppmtool.repositories.ProjectRepository;
import com.reactivemanuel.ppmtool.repositories.UserRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository 	projectRepository;	
	@Autowired
	private BacklogRepository 	backlogRepository;
	@Autowired
	private UserRepository 		userRepository;
	
	public Project saveOrUpdateProject(Project project, String username) {		
		
		if(project.getId() != null){
			Optional<Project> existingProject = projectRepository.findById(project.getId());
//            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if(existingProject.isPresent() && 
            		(!existingProject.get().getProjectLeader().equals(username) 
            				|| !project.getProjectIdentifier().equals(existingProject.get().getProjectIdentifier())) ){
                throw new ProjectNotFoundException("Project not found in your account");
            }else if(!existingProject.isPresent()){
                throw new ProjectNotFoundException("Project with ID: '"+project.getProjectIdentifier()+"' cannot be updated because it doesn't exist");
            }
        }		
		
		try{

            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId()==null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId()!=null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);

        }catch (Exception e){
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }
		

	}
	
	public Project findProjectByIdentifier(String projectIdentifier, String username) {
		Project project = projectRepository.findByProjectIdentifier(projectIdentifier);
		
//		if(project==null || !project.getProjectLeader().contentEquals(username)) {
		if(project==null) {
			throw new ProjectIdException("Project Identifier '" + projectIdentifier + "' does not exists.");
		}	
		
		if(!project.getProjectLeader().contentEquals(username)) {
			throw new ProjectNotFoundException("Project not found in your account.");
		}	
		
		return project;
	}
	
	public List<Project> findAllProjects(String username){
		return projectRepository.findAllByProjectLeader(username);
	}
	
	public Project deleteProjectByIdentifier(String projectIdentifier, String username) {
		Project project = this.findProjectByIdentifier(projectIdentifier, username);

		projectRepository.delete(project);
		return project;
	}
	
}
