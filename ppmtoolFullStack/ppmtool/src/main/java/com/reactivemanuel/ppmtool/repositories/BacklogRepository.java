package com.reactivemanuel.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.reactivemanuel.ppmtool.domain.Backlog;
import com.reactivemanuel.ppmtool.domain.Project;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long>{

}
