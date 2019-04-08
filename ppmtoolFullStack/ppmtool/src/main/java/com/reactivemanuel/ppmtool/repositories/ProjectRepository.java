package com.reactivemanuel.ppmtool.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.reactivemanuel.ppmtool.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>{	
	
	Project findByProjectIdentifier(String projectIdentifier);	

	@Override
	List<Project> findAll();
	
	List<Project> findAllByProjectLeader(String username);
}
