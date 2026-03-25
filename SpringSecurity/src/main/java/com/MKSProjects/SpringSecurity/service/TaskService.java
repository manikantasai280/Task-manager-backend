package com.MKSProjects.SpringSecurity.service;

import com.MKSProjects.SpringSecurity.entity.Task;
import com.MKSProjects.SpringSecurity.repo.TaskRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepo taskRepo;
    public Task createTask(Task task){
        return taskRepo.save(task);
    }
    public Optional<Task> findById(Long id){
        return taskRepo.findById(id);
    }
    public List<Task> getAllTasks(){
        return taskRepo.findAll();
    }
    public List<Task> findByUserId(Long userid){
        return taskRepo.findByUser_Id(userid);
    }
    public void deleteById(Long id){
        taskRepo.deleteById(id);
    }
}
