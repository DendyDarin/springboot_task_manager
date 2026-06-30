package com.darin.taskmanager.service;

import com.darin.taskmanager.dto.ApiResponse;
import com.darin.taskmanager.dto.TaskDTO;
import com.darin.taskmanager.enums.Category;

import java.util.List;

public interface TaskService {

    ApiResponse<TaskDTO> createTask(TaskDTO taskDetails, String userEmail);
    ApiResponse<List<TaskDTO>> getTasksByUserAndCategory(String userEmail, Category category);
    ApiResponse<String> deleteTask(Long id, String userEmail);
    ApiResponse<TaskDTO> toggleTaskCompletion(Long id, String userEmail);
}
