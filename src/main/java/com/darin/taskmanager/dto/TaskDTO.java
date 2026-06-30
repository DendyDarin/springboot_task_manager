package com.darin.taskmanager.dto;

import com.darin.taskmanager.enums.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDTO {

    private Long id;

    private String title;

    private String description;

    private Category category;

    private boolean isCompleted;

    // private UserDTO user;
}
