package com.MKSProjects.SpringSecurity.controller;

import com.MKSProjects.SpringSecurity.dto.TaskRequestDTO;
import com.MKSProjects.SpringSecurity.dto.TaskResponseDTO;
import com.MKSProjects.SpringSecurity.entity.Task;
import com.MKSProjects.SpringSecurity.entity.User;
import com.MKSProjects.SpringSecurity.service.TaskService;
import com.MKSProjects.SpringSecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    // 🔹 Get logged-in username
    private String getLoggedInUsername() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    // ✅ CREATE TASK
    @PostMapping
    public TaskResponseDTO createTask(@RequestBody TaskRequestDTO dto){

        String username = getLoggedInUsername();

        User user = userService.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setUser(user);

        Task saved = taskService.createTask(task);

        return mapToResponse(saved);
    }

    // ✅ GET MY TASKS
    @GetMapping
    public List<TaskResponseDTO> getMyTasks(){

        String username = getLoggedInUsername();

        User user = userService.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Task> tasks = taskService.findByUserId(user.getId());

        return tasks.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ✅ GET TASK BY ID (secured)
    @GetMapping("/{id}")
    public TaskResponseDTO getTaskById(@PathVariable Long id){

        String username = getLoggedInUsername();

        User user = userService.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskService.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access Denied");
        }

        return mapToResponse(task);
    }

    // ✅ UPDATE TASK (secured)
    @PutMapping("/{id}")
    public TaskResponseDTO updateTask(@PathVariable Long id,
                                      @RequestBody TaskRequestDTO dto){

        String username = getLoggedInUsername();

        User user = userService.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task existingTask = taskService.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!existingTask.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access Denied");
        }

        existingTask.setTitle(dto.getTitle());
        existingTask.setDescription(dto.getDescription());
        existingTask.setStatus(dto.getStatus());

        Task updated = taskService.createTask(existingTask);

        return mapToResponse(updated);
    }

    // ✅ DELETE TASK (secured)
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id){

        String username = getLoggedInUsername();

        User user = userService.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskService.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access Denied");
        }

        taskService.deleteById(id);

        return "Task deleted successfully";
    }

    // 🔹 Mapper method (Entity → DTO)
    private TaskResponseDTO mapToResponse(Task task){
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setUsername(task.getUser().getUsername());
        return dto;
    }
}