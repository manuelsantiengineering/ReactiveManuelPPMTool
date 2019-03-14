package com.reactivemanuel.ppmtool.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Backlog {
	
	// One-to-One with the Project
	// One-to-Many with the Project Tasks
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	private Integer ProjectTaskSequence = 0;	
	private String projectIdentifier;
	
	// JsonIgnore breaks the infinite recursion problem
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="project_id",nullable=false)
	@JsonIgnore
	private Project project;
	
	// JsonIgnore breaks the infinite recursion problem
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="backlog")
	private List<ProjectTask> projectTasks = new ArrayList<>();
	
	public Backlog() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getProjectTaskSequence() {
		return ProjectTaskSequence;
	}

	public void setProjectTaskSequence(Integer pTSequence) {
		ProjectTaskSequence = pTSequence;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<ProjectTask> getProjectTasks() {
		return projectTasks;
	}

	public void setProjectTasks(List<ProjectTask> projectTasks) {
		this.projectTasks = projectTasks;
	}

	
	
	
}
