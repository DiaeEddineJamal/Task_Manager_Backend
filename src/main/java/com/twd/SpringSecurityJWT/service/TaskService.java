package com.twd.SpringSecurityJWT.service;

import com.twd.SpringSecurityJWT.entity.*;
import com.twd.SpringSecurityJWT.repository.OurUserRepo;
import com.twd.SpringSecurityJWT.repository.TaskRepository;
import com.twd.SpringSecurityJWT.repository.ProjectRepository;
import com.twd.SpringSecurityJWT.dto.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {


    @Autowired
    private final TaskRepository taskRepository;

    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final OurUserRepo ourUserRepo;

    // Create a new task
    public Tasks createTask(TaskDTO taskDTO) {
        Project project = projectRepository.findById(taskDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + taskDTO.getProjectId()));
        OurUsers user = ourUserRepo.findById(taskDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + taskDTO.getUserId()));
        Status status = parseStatus(taskDTO.getStatus());
        Priority priority = parsePriority(taskDTO.getPriority());


        Tasks task = new Tasks();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setEstimatedEndtime(taskDTO.getEstimatedEndtime());
        task.setEndtime(taskDTO.getEndtime());
        task.setStatus(status);
        task.setPriority(priority);
        task.setProject(project);
        task.setUser(user);

        

        return taskRepository.save(task);
    }

    // Update an existing task
    public Tasks updateTask(int id, TaskDTO taskDTO) {
        Tasks task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));

        Status status = parseStatus(taskDTO.getStatus());
        Priority priority = parsePriority(taskDTO.getPriority());

        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setEstimatedEndtime(taskDTO.getEstimatedEndtime());
        task.setEndtime(taskDTO.getEndtime());
        task.setStatus(status);
        task.setPriority(priority);

        return taskRepository.save(task);
    }

    // Get all tasks
    public List<Tasks> getAllTasks() {
        return taskRepository.findAll();
    }

    // Get a task by ID
    public Tasks getTaskById(int id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));
    }

    // Delete a task by ID
    public void deleteTask(int id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
    }

    // Helper method to parse the status string to Status enum
    private Status parseStatus(String status) {
        try {
            return Status.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }
    }

    // Helper method to parse the priority string to Priority enum
    private Priority parsePriority(String priority) {
        try {
            return Priority.valueOf(priority);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid priority: " + priority);
        }
    }
}
