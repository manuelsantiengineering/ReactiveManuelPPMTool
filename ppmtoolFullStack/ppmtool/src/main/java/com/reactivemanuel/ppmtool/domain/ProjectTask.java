package com.reactivemanuel.ppmtool.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
//import io.swagger.annotations.ApiModelProperty.AccessMode;

@Entity
@ApiModel(value="ProjectTask", description="Project Task model for the documentation")
public class ProjectTask {

	// Many-to-One with the Backlog
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@ApiModelProperty(accessMode = AccessMode.READ_ONLY,position=1)
	private Long id;
	@Column(updatable=false, unique=true)
	@ApiModelProperty(required=true,notes="Auto-generated counter of Project Tasks in a Project.",example="1",position=2)
	private String projectSequence;
	@NotBlank(message="Please include a project summary")
	@ApiModelProperty(required=true,example="\"Summary of the Project Task.\"",allowEmptyValue=false,position=4)
	private String summary;
	@ApiModelProperty(required=true,example="\"100% Described code.\"",position=5)
	private String acceptanceCriteria;
	@ApiModelProperty(notes="Default: TO DO",example="DONE",position=6)
	private String status;
	@ApiModelProperty(notes="Default: LOW",example="HIGH",position=7)
	private Integer priority;
	@JsonFormat(pattern="yyyy-mm-dd")
	@ApiModelProperty(example = "2019-05-01",position=8)
	private Date dueDate;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="backlog_id",updatable=false,nullable=false)
	@JsonIgnore
	@ApiModelProperty(hidden=true)
	private Backlog backlog;
	
	@Column(updatable=false)
	@ApiModelProperty(required=true,notes="Combines the Project Identifier and the Project Task Sequence.",example="SID01-1",position=3)
	private String projectIdentifier;	
	@JsonFormat(pattern="yyyy-mm-dd")
	@Column(updatable=false)
//	@ApiModelProperty(accessMode = AccessMode.READ_ONLY,example = "2019-04-04",position=9)
	@ApiModelProperty(example = "2019-04-04",position=9)
	private Date created_At;
	@JsonFormat(pattern="yyyy-mm-dd")
//	@ApiModelProperty(accessMode = AccessMode.READ_ONLY,example = "2019-04-10",position=10)
	@ApiModelProperty(example = "2019-04-10",position=10)
	private Date updated_At;
	
	public ProjectTask() {
		super();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectSequence() {
		return projectSequence;
	}

	public void setProjectSequence(String projectSequence) {
		this.projectSequence = projectSequence;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}

	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
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

	@PrePersist
	protected void onCreate() {
		this.created_At = new Date();
		this.updated_At = new Date();
	}
	
	@PreUpdate	
	protected void onUpdate() {
		this.updated_At = new Date();
	}

	@Override
	public String toString() {
		return "ProjectTask [id=" + id + ", projectSequence=" + projectSequence + ", summary=" + summary
				+ ", acceptanceCriteria=" + acceptanceCriteria + ", status=" + status + ", priority=" + priority
				+ ", dueDate=" + dueDate + ", projectIdentifier=" + projectIdentifier + ", created_At=" + created_At
				+ ", updated_At=" + updated_At + "]";
	}

}
