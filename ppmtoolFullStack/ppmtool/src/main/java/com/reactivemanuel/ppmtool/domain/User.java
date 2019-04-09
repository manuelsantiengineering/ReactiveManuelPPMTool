package com.reactivemanuel.ppmtool.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
//import io.swagger.annotations.ApiModelProperty.AccessMode;

@Entity
@ApiModel(value="User", description="Project model for the documentation")
public class User implements UserDetails{

	private static final long serialVersionUID = 3256080218380213238L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//	@ApiModelProperty(accessMode = AccessMode.READ_ONLY,position=1)
    private Long id;
	
	@Email(message= "Username needs to be an email.")	
	@NotBlank(message="Username is required.")
	@Column(unique=true,updatable=false)
	@ApiModelProperty(required=true,notes="The username must be an email.",example="username@email.com",position=2)
	private String username;
	@NotBlank(message="Please enter your first name.")
	@ApiModelProperty(required=true,example="Username",position=3)
	private String firstName;
	@NotBlank(message="Password is required.")
	@ApiModelProperty(required=true,notes="Password is encrypted.",position=4)
	private String password;
	@Transient
//	@NotBlank(message="Confirmation password is required.")
	@ApiModelProperty(required=true,notes="Confirmation password",position=5)
	private String confirmPassword;
	
//	@JsonFormat(pattern="yyyy-mm-dd")
//	@ApiModelProperty(accessMode = AccessMode.READ_ONLY, example = "2019-04-04",position=6)
	@ApiModelProperty(example = "2019-04-04",position=6)
	private Date created_At;
//	@JsonFormat(pattern="yyyy-mm-dd")
//	@ApiModelProperty(accessMode = AccessMode.READ_ONLY, example = "2019-04-04",position=7)
	@ApiModelProperty(example = "2019-04-04",position=7)
	private Date updated_At;

	//OneToMany with the project
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
	@ApiModelProperty(hidden=true)
	private List<Project> projects = new ArrayList<>();
	
	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

    public void setUpdate_At(Date updated_At) {
        this.updated_At = updated_At;
    }
	
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
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
	
	/*
	 UserDetails interface methods
	 */	

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
	
}
