package com.enriqueforte.code_review_service.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    private String name;
    private String repoUrl;

        // Relación 1:N con Issue
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Issue> issues;


    public Project() {}

    public Project(String name, String reporUrl) {
        this.name = name;
        this.repoUrl = reporUrl;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }    

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String reporUrl) {
        this.repoUrl = reporUrl;
 
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
