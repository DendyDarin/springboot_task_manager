package com.darin.taskmanager.service.implementation;

import com.darin.taskmanager.dto.ApiResponse;
import com.darin.taskmanager.dto.TaskDTO;
import com.darin.taskmanager.enums.Category;
import com.darin.taskmanager.exception.BadRequestException;
import com.darin.taskmanager.exception.NotFoundException;
import com.darin.taskmanager.model.Task;
import com.darin.taskmanager.model.User;
import com.darin.taskmanager.repository.TaskRepository;
import com.darin.taskmanager.repository.UserRepository;
import com.darin.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImplementation implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse<TaskDTO> createTask(TaskDTO taskDetails, String userEmail) {

        // Validate user
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("User not found"));

        // Create Task logic
        Task newTask = new Task();
        newTask.setTitle(taskDetails.getTitle());
        newTask.setDescription(taskDetails.getDescription());
        newTask.setCategory(taskDetails.getCategory() != null ? taskDetails.getCategory() : Category.PERSONAL);
        newTask.setUser(user);

        Task savedTask = taskRepository.save(newTask);
        TaskDTO savedTaskDTO = mapTaskToTaskDTO(savedTask);

        return new ApiResponse<>(201, "Task created successfully", savedTaskDTO);
    }

    @Override
    public ApiResponse<List<TaskDTO>> getTasksByUserAndCategory(String userEmail, Category category) {

        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("User not found"));

        List<Task> tasks;
        if (category != null) {
            tasks = taskRepository.findByUserAndCategory(user, category);
        } else {
            tasks = taskRepository.findByUser(user);
        }

        List<TaskDTO> taskDTOS = tasks.stream()
            .map(this::mapTaskToTaskDTO)
            .toList();

        String message = (category != null) ? "User task category: " + category : "All tasks has been retrieved";

        return new ApiResponse<>(200, message, taskDTOS);
    }

    @Override
    public ApiResponse<String> deleteTask(Long id, String userEmail) {

        if (!taskRepository.existsById(id)) throw new NotFoundException("Task not found");

        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));

        if (!task.getUser().getEmail().equals(userEmail)) {
            throw new BadRequestException("You aren't authorized to delete this task");
        }

        taskRepository.deleteById(id);

        return new ApiResponse<>(204, "Task deleted successfully", null);
    }

    @Override
    public ApiResponse<TaskDTO> toggleTaskCompletion(Long id, String userEmail) {

        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));

        if (!task.getUser().getEmail().equals(userEmail)) {
            throw new BadRequestException("You aren't authorized to update this task");
        }

        task.setCompleted(!task.isCompleted());

        Task updatedTask = taskRepository.save(task);
        String status = updatedTask.isCompleted() ? "complete task" : "pending task";

        return new ApiResponse<>(200, "Task marked as " + status, mapTaskToTaskDTO(updatedTask));
    }

    private TaskDTO mapTaskToTaskDTO(Task task) {

        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setCategory(task.getCategory());
        taskDTO.setCompleted(task.isCompleted());

        return taskDTO;
    }
}
