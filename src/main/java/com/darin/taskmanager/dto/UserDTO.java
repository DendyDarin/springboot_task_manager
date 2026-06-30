package com.darin.taskmanager.dto;

import com.darin.taskmanager.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true) // will ignore if fields are empty
public class UserDTO {

    private Long id;

    private String email;

    private String password;

    private Role role;

    private List<TaskDTO> tasks;
}
