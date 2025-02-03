package com.enriqueforte.code_review_service.controller;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.enriqueforte.code_review_service.model.Issue;
import com.enriqueforte.code_review_service.model.Project;
import com.enriqueforte.code_review_service.repository.IssueRepository;
import com.enriqueforte.code_review_service.repository.ProjectRepository;
import com.enriqueforte.code_review_service.service.CodeReviewService;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

@Controller
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private CodeReviewService codeReviewService;

    // Landing page: Carátula principal
    @GetMapping("/")
    public String home() {
        return "home"; // Renderiza templates/home.html
    }

    // Página principal de gestión de proyectos
    @GetMapping("/index")
    public String index(Model model) {
        List<Project> projects = projectRepository.findAll();
        model.addAttribute("projects", projects);
        model.addAttribute("newProject", new Project());
        return "index"; // Renderiza templates/index.html
    }

    @PostMapping("/projects")
    public String createProject(@ModelAttribute("newProject") Project project) {
        projectRepository.save(project);
        return "redirect:/index";
    }

    @GetMapping("/projects/{projectId}/review")
    public String reviewProject(@PathVariable Long projectId) throws GitAPIException, CheckstyleException, IOException {
        Project project = projectRepository.findById(projectId).orElseThrow();
        codeReviewService.cloneRepository(project);
        codeReviewService.runCheckstyle(project);
        return "redirect:/index";
    }

    @GetMapping("/projects/{projectId}/issues")
    public String getIssues(@PathVariable Long projectId, Model model) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        List<Issue> issues = issueRepository.findByProjectId(projectId);
        model.addAttribute("project", project);
        model.addAttribute("issues", issues);
        return "issues"; // Renderiza templates/issues.html
    }

    @GetMapping("/projects/{projectId}/delete")
    public String deleteProject(@PathVariable Long projectId) {
        projectRepository.deleteById(projectId);
        return "redirect:/index";
    }

    @GetMapping("/projects/{projectId}/edit")
    public String editProject(@PathVariable Long projectId, Model model) {
        Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        model.addAttribute("project", project);
        return "editProject";// se espera que exista templates/editProject.html
    }

    @PostMapping("/projects/{projectId}/edit")
    public String updateProject(@PathVariable Long projectId, @ModelAttribute("project") Project updatProject) {
        // recuperar el proyecto existente
        Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        // actualizar los campos del proyecto
        project.setName(updatProject.getName());
        // aca podriamos actualizar otros campos
        projectRepository.save(project);

        return "redirect:/index";
    }


    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Long totalProjects = projectRepository.count();
        Long totalIssues = issueRepository.count();
        List<Object[]> issuesPerProject = issueRepository.countIssuesPerProject();
        model.addAttribute("totalProjects", totalProjects);
        model.addAttribute("totalIssues", totalIssues);
        model.addAttribute("issuesPerProject", issuesPerProject);
        return "dashboard"; // Renderiza templates/dashboard.html
    }
}