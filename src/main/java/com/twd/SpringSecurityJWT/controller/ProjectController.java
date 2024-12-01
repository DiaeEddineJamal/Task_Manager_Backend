package com.twd.SpringSecurityJWT.controller;

import com.twd.SpringSecurityJWT.entity.Project;
import com.twd.SpringSecurityJWT.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.twd.SpringSecurityJWT.dto.ReqRes;
import com.twd.SpringSecurityJWT.entity.Product;
import com.twd.SpringSecurityJWT.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Get a project by ID (Accessible only by users with role USER)

    @GetMapping("/user/projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable int id) {
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }


    // Get all projects (Accessible only by users with role USER)

    @GetMapping("/user/projects/getall")
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    // Add a new project (Accessible only by users with role USER)

    @PostMapping("/user/projects/addproject")
    public ResponseEntity<Project> addProject(@RequestBody Project project) {
        Project savedProject = projectService.addProject(project);
        return ResponseEntity.ok(savedProject);
    }

    // Update a project by ID (Accessible only by users with role USER)

    @PutMapping("/user/projects/update/{id}")
    public ResponseEntity<Project> updateProjectById(@PathVariable int id, @RequestBody Project updatedProject) {
        Project project = projectService.updateProjectById(id, updatedProject);
        return ResponseEntity.ok(project);
    }

    // Delete a project by ID (Accessible only by users with role USER)
    @DeleteMapping("/user/projects/delete/{id}")
    public ResponseEntity<String> deleteProjectById(@PathVariable int id) {
        projectService.deleteProjectById(id);
        return ResponseEntity.ok("Project with ID " + id + " has been deleted successfully.");
    }
}
