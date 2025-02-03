package com.enriqueforte.code_review_service.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enriqueforte.code_review_service.model.Issue;
import com.enriqueforte.code_review_service.model.Project;
import com.enriqueforte.code_review_service.repository.IssueRepository;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

@Service
public class CodeReviewService {
    
    @Autowired
    private IssueRepository issueRepository;

    public void cloneRepository(Project project) throws GitAPIException {
        // Clone the repository
        File localPath = new File("temp/" + project.getName());

        if (localPath.exists()) {
            deleteDirectory(localPath);
        }

        Git.cloneRepository()
            .setURI(project.getRepoUrl())
            .setDirectory(localPath)
            .call()
            .close(); 
    }

    public List<Issue> runCheckstyle(Project project) throws CheckstyleException, IOException {
        // Configuración de Checkstyle
        // Ruta local donde se clone el repositorio
        File localPath = new File("temp/" + project.getName());

        // Carga la configuración de Checkstyle
        String configPath = "src/main/resources/checkstyle/google_checks.xml";
        Configuration checkstyleConfig = ConfigurationLoader.loadConfiguration(
            configPath,
            new PropertiesExpander(System.getProperties())            
        );

        // Crar e inicializar el analizador de Checkstyle
        Checker checker = new Checker();
        // Necesario para que Checkstyle pueda encontrar las clases que necesita
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        checker.configure(checkstyleConfig);

        // Lista de issues encontrados
        List<Issue> foundIssues = new ArrayList<>();

        // Añadir un audiolistener para que Checkstyle devuelva los errores
        checker.addListener(new AuditListener() {
            @Override
            public void auditStarted(AuditEvent event) {
                // No hacer nada
            }

            @Override
            public void auditFinished(AuditEvent event) {
                // No hacer nada
            }

            @Override
            public void fileStarted(AuditEvent event) {
                // No hacer nada
            }

            @Override
            public void fileFinished(AuditEvent event) {
                // No hacer nada
            }

            @Override
            public void addError(AuditEvent event) {
                // Aquí checkstyle reporta un "error"(violación de regla)
                String fileName = event.getFileName();                
                int line = event.getLine();
                String message = event.getMessage();

                Issue issue = new Issue();
                issue.setFilePath(fileName);
                issue.setLineNumber(line);
                issue.setMessage(message);
                issue.setProject(project);
                
                

                foundIssues.add(issue);
            }

            @Override
            public void addException(AuditEvent event, Throwable throwable) {
                // Aquí puedes agregar lógica para manejar excepciones
            }
        });

        // Recopilar la Lista de archivos.java para pasasrlos a Checkstyle
        List<File> javaFiles = Files.walk(localPath.toPath())
            .filter(path -> path.toString().endsWith(".java"))
            .map(Path::toFile)
            .collect(Collectors.toList());

        // Procesar los archivos con Checkstyle
        checker.process(javaFiles);
        
        // Destruir el checker al terminar
        checker.destroy();

        // Guardar los issues en la base de datos
        issueRepository.saveAll(foundIssues);  

        return foundIssues;
     
    }

    private void deleteDirectory(File file) {
        if (file.isDirectory()) {
            for (File sub : file.listFiles()) {
                deleteDirectory(sub);
            }
        }
        file.delete();
    }
    
    
}
