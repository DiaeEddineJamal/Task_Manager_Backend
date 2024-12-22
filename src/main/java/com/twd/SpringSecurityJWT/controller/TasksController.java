package com.twd.SpringSecurityJWT.controller;

import com.twd.SpringSecurityJWT.entity.Tasks;
import com.twd.SpringSecurityJWT.dto.TaskDTO;
import com.twd.SpringSecurityJWT.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user/tasks") // General path for tasks
public class TasksController {

    @Autowired
    private TaskService taskService;

    // Get a task by ID (Accessible only by users with role USER)
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable int id) {
        TaskDTO taskDTO = taskService.getTaskById(id);
        return ResponseEntity.ok(taskDTO);
    }

    // Get all tasks (Accessible only by users with role USER)
    @GetMapping("/getall")
    public ResponseEntity<List<Tasks>> getAllTasks() {
        List<Tasks> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // Add a new task (Accessible only by users with role USER)
    @PostMapping("/addtask")
    public ResponseEntity<Tasks> addTask(@RequestBody TaskDTO taskDTO) {
        Tasks savedTask = taskService.createTask(taskDTO);
        return ResponseEntity.ok(savedTask);
    }

    // Update a task by ID (Accessible only by users with role USER)
    @PutMapping("/update/{id}")
    public ResponseEntity<Tasks> updateTaskById(@PathVariable int id, @RequestBody TaskDTO updatedTask) {
        Tasks task = taskService.updateTask(id, updatedTask);
        return ResponseEntity.ok(task);
    }

    // Delete a task by ID (Accessible only by users with role USER)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTaskById(@PathVariable int id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task with ID " + id + " has been deleted successfully.");
    }
}
