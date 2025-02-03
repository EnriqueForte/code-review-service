package com.enriqueforte.code_review_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.enriqueforte.code_review_service.model.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByProjectId(Long projectId);

    // Devuelve una lista de Object[], donde cada elemento contiene: [nombreProyecto, count]
    @Query("SELECT p.name, COUNT(i) FROM Issue i JOIN i.project p GROUP BY p.name")
    List<Object[]> countIssuesPerProject();
    
}
