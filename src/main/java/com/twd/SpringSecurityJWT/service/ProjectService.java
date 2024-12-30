package com.twd.SpringSecurityJWT.service;

import com.twd.SpringSecurityJWT.entity.Project;
import com.twd.SpringSecurityJWT.entity.OurUsers;
import com.twd.SpringSecurityJWT.entity.Tasks;
import com.twd.SpringSecurityJWT.entity.Status;
import com.twd.SpringSecurityJWT.repository.ProjectRepository;
import com.twd.SpringSecurityJWT.repository.OurUserRepo;
import com.twd.SpringSecurityJWT.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private OurUserRepo userRepository;

    @Autowired
    private TaskRepository taskRepository;

    private LocalDateTime threeDaysFromNow;

    public Project getProjectById(int id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project addProject(Project project) {
        if (project.getStatus() == null) {
            project.setStatus(Status.PENDING);
        }
        return projectRepository.save(project);
    }

    public Project updateProjectById(int id, Project updatedProject) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));

        existingProject.setName(updatedProject.getName());
        existingProject.setBudget(updatedProject.getBudget());
        existingProject.setDescription(updatedProject.getDescription());
        existingProject.setEstimatedEndtime(updatedProject.getEstimatedEndtime());
        existingProject.setEndtime(updatedProject.getEndtime());

        if (updatedProject.getStatus() != null) {
            existingProject.setStatus(updatedProject.getStatus());
        }

        return projectRepository.save(existingProject);
    }

    public void deleteProjectById(int id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project not found with ID: " + id);
        }
        projectRepository.deleteById(id);
    }

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

    public Project removeTeamMember(int projectId, int userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));

        OurUsers user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        project.getTeam().remove(user);
        return projectRepository.save(project);
    }

    public List<OurUsers> getProjectTeam(int projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));

        return project.getTeam();
    }

    public List<Project> getOverdueProjects(LocalDateTime threeDaysFromNow) {
        this.threeDaysFromNow = threeDaysFromNow;
        LocalDateTime now = LocalDateTime.now();
        threeDaysFromNow = now.plusDays(3);
        return projectRepository.findByEstimatedEndtimeBefore(threeDaysFromNow);
    }

    public double calculateProjectProgress(int projectId) {
        List<Tasks> tasks = taskRepository.findByProjectId(projectId);

        if (tasks.isEmpty()) {
            return 0.0;
        }

        long completedTasks = tasks.stream()
                .filter(task -> task.getStatus() == Status.COMPLETED)
                .count();

        return ((double) completedTasks / tasks.size()) * 100;
    }

    public void completeProject(int projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));

        List<Tasks> tasks = project.getTasks();
        boolean allTasksCompleted = tasks.stream().allMatch(task -> task.getStatus() == Status.COMPLETED);

        if (allTasksCompleted) {
            project.setStatus(Status.COMPLETED);
            project.setEndtime(LocalDateTime.now());
            projectRepository.save(project);
        } else {
            throw new RuntimeException("Cannot complete project. Not all tasks are completed.");
        }
    }

    public void updateProjectStatus(int projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));

        List<Tasks> tasks = project.getTasks();
        boolean allTasksCompleted = tasks.stream().allMatch(task -> task.getStatus() == Status.COMPLETED);

        if (allTasksCompleted && project.getStatus() != Status.COMPLETED) {
            project.setStatus(Status.COMPLETED);
            project.setEndtime(LocalDateTime.now());
        } else if (!allTasksCompleted && project.getStatus() == Status.COMPLETED) {
            project.setStatus(Status.IN_PROGRESS);
            project.setEndtime(null);
        }

        projectRepository.save(project);
    }
}