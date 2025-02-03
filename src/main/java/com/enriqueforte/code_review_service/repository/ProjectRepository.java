package com.enriqueforte.code_review_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.enriqueforte.code_review_service.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {   
}

