package com.reactivemanuel.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.reactivemanuel.ppmtool.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>{	
	
	Project findByProjectIdentifier(String projectIdentifier);	

	@Override
	Iterable<Project> findAll();
}
