package com.twd.SpringSecurityJWT.service;

import com.twd.SpringSecurityJWT.entity.Project;
import com.twd.SpringSecurityJWT.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    // Get a project by ID
    public Project getProjectById(int id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));
    }
    
    // Get all projects
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Add a new project
    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    // Update a project by ID
    public Project updateProjectById(int id, Project updatedProject) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));

        existingProject.setName(updatedProject.getName());
        existingProject.setBudget(updatedProject.getBudget());
        existingProject.setDescription(updatedProject.getDescription());
        existingProject.setEstimatedEndtime(updatedProject.getEstimatedEndtime());
        existingProject.setEndtime(updatedProject.getEndtime());

        return projectRepository.save(existingProject);
    }

    // Delete a project by ID
    public void deleteProjectById(int id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project not found with ID: " + id);
        }
        projectRepository.deleteById(id);
    }
}
