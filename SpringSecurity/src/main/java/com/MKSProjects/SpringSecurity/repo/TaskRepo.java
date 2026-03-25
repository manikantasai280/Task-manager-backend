package com.MKSProjects.SpringSecurity.repo;

import com.MKSProjects.SpringSecurity.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task,Long>{
        List<Task> findByUser_Id(Long userId);
    }
