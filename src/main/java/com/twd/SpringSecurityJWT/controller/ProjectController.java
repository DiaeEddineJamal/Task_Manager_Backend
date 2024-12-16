package com.twd.SpringSecurityJWT.controller;

import com.twd.SpringSecurityJWT.dto.TeamMemberRequest;
import com.twd.SpringSecurityJWT.entity.Project;
import com.twd.SpringSecurityJWT.entity.OurUsers;
import com.twd.SpringSecurityJWT.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/user/projects/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Project> getProjectById(@PathVariable int id) {
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/user/projects/getall")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @PostMapping("/user/projects/addproject")
    @PreAuthorize("hasRole('USER', 'ADMIN')")
    public ResponseEntity<Project> addProject(@RequestBody Project project) {
        Project savedProject = projectService.addProject(project);
        return ResponseEntity.ok(savedProject);
    }

    @PutMapping("/user/projects/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> updateProjectById(@PathVariable int id, @RequestBody Project updatedProject) {
        Project project = projectService.updateProjectById(id, updatedProject);
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/user/projects/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProjectById(@PathVariable int id) {
        projectService.deleteProjectById(id);
        return ResponseEntity.ok("Project with ID " + id + " has been deleted successfully.");
    }

    @GetMapping("/{projectId}/team")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<OurUsers>> getProjectTeam(@PathVariable int projectId) {
        List<OurUsers> team = projectService.getProjectTeam(projectId);
        return ResponseEntity.ok(team);
    }

    @PostMapping("/{projectId}/team")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> addTeamMember(
            @PathVariable int projectId,
            @RequestBody TeamMemberRequest teamMemberRequest) {
        Project updatedProject = projectService.addTeamMember(projectId, teamMemberRequest.getUserId());
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{projectId}/team")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> removeTeamMember(
            @PathVariable int projectId,
            @RequestBody TeamMemberRequest teamMemberRequest) {
        Project updatedProject = projectService.removeTeamMember(projectId, teamMemberRequest.getUserId());
        return ResponseEntity.ok(updatedProject);
    }
}