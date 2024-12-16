package com.twd.SpringSecurityJWT.service;

import com.twd.SpringSecurityJWT.entity.Project;
import com.twd.SpringSecurityJWT.entity.OurUsers;
import com.twd.SpringSecurityJWT.repository.ProjectRepository;
import com.twd.SpringSecurityJWT.repository.OurUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private OurUserRepo userRepository;

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
        existingProject.setTeam(updatedProject.getTeam());

        return projectRepository.save(existingProject);
    }

    // Delete a project by ID
    public void deleteProjectById(int id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project not found with ID: " + id);
        }
        projectRepository.deleteById(id);
    }

    // Add a user to project team
    public Project addTeamMember(int projectId, int userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));

        OurUsers user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        if (!project.getTeam().contains(user)) {
            project.getTeam().add(user);
            return projectRepository.save(project);
        }
        return project;
    }

    // Remove a user from project team
    public Project removeTeamMember(int projectId, int userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));

        OurUsers user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        project.getTeam().remove(user);
        return projectRepository.save(project);
    }

    // Get all team members of a project
    public List<OurUsers> getProjectTeam(int projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));

        return project.getTeam();
    }
}