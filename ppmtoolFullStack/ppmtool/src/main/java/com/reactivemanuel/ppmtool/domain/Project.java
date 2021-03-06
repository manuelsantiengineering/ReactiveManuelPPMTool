package com.reactivemanuel.ppmtool.domain;


import java.util.Date;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
//import io.swagger.annotations.ApiModelProperty.AccessMode;

@Entity
@ApiModel(value="Project", description="Project model for the documentation")
public class Project {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	@ApiModelProperty(accessMode = AccessMode.READ_ONLY,position=1)
	@ApiModelProperty(hidden=true)
	private Long id;
	
	@NotBlank(message="Project name is required.")
	@ApiModelProperty(required=true, example="Swagger Project 01",position=2)
	private String 	projectName;	
	@NotBlank(message="Project identified is required.")
	@Size(min=3, max=6,message="Please use 3 to 6 characters.")
	@Column(updatable=false,unique=true)
	@ApiModelProperty(required=true, example="SID01",position=3)
	private String 	projectIdentifier;	
	@NotBlank(message="Project description is required.")
	@ApiModelProperty(required=true, example="Swagger Project Description",position=4)
	private String 	description;		
	@JsonFormat(pattern="yyyy-mm-dd")
	@ApiModelProperty(example = "2019-04-04",position=5)
	private Date 	start_date;	
	@JsonFormat(pattern="yyyy-mm-dd")
	 @ApiModelProperty(example = "2019-05-04",position=6)
	private Date 	end_date;	
	
	@JsonFormat(pattern="yyyy-mm-dd")	
	@Column(updatable=false)
//	@ApiModelProperty(accessMode = AccessMode.READ_ONLY, example = "2019-04-04",position=7)
	@ApiModelProperty(example = "2019-04-04",position=7)
	private Date 	created_At;	
	@JsonFormat(pattern="yyyy-mm-dd")
//	@ApiModelProperty(accessMode = AccessMode.READ_ONLY, example = "2019-04-04",position=8)
	@ApiModelProperty(example = "2019-04-04",position=8)
	private Date 	updated_At;
	
	// "cascade=CascadeType.ALL" makes sure that the project is the
	// owning side of the relationship. So if the project is
	// deleted, the backlog is also deleted.
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
    @JsonIgnore
    @ApiModelProperty(hidden=true)
    private Backlog backlog;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	@ApiModelProperty(required=true, example="username@email.com",position=9)
	private User user;
	
	@ApiModelProperty(required=true, example="username@email.com",position=10)
	private String projectLeader;
	
	public Project() {
		super();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Date getCreated_At() {
		return created_At;
	}

	public void setCreated_At(Date created_At) {
		this.created_At = created_At;
	}

	public Date getUpdated_At() {
		return updated_At;
	}

	public void setUpdated_At(Date updated_At) {
		this.updated_At = updated_At;
	}

	public Backlog getBacklog() {
		return backlog;
	}

	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getProjectLeader() {
		return projectLeader;
	}

	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}

	@PrePersist
	protected void onCreate() {
		this.created_At = new Date();
		this.updated_At = new Date();
	}
	
	@PreUpdate	
	protected void onUpdate() {
		this.updated_At = new Date();
	}
}
