package com.darin.taskmanager.service;

import com.darin.taskmanager.dto.ApiResponse;
import com.darin.taskmanager.dto.UserDTO;

import java.util.List;

public interface UserService {
    ApiResponse<List<UserDTO>> getAllUsers();
    ApiResponse<UserDTO> getUserProfile(String userEmail);
    ApiResponse<UserDTO> updateUserProfile(String userEmail, UserDTO userDTO);
}
