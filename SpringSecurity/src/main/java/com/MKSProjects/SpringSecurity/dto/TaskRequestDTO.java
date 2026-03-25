package com.MKSProjects.SpringSecurity.dto;

import lombok.Data;

@Data
public class TaskRequestDTO {
    private String title;
    private String description;
    private String status;
}