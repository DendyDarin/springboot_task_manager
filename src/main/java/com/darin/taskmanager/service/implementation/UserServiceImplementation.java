package com.darin.taskmanager.service.implementation;

import com.darin.taskmanager.dto.ApiResponse;
import com.darin.taskmanager.dto.UserDTO;
import com.darin.taskmanager.exception.NotFoundException;
import com.darin.taskmanager.model.User;
import com.darin.taskmanager.repository.UserRepository;
import com.darin.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    @Override
    public ApiResponse<List<UserDTO>> getAllUsers() {

        List<UserDTO> userDTOS = userRepository.findAll().stream()
            .map(this::mapUserToUserDTO)
            .toList();

        return new ApiResponse<>(200, "All users retrieved successfully", userDTOS);
    }

    @Override
    public ApiResponse<UserDTO> getUserProfile(String userEmail) {

        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("User not found"));

        return new ApiResponse<UserDTO>(200, "User " + userEmail + " retrieved successfully", mapUserToUserDTO(user));
    }

    @Override
    public ApiResponse<UserDTO> updateUserProfile(String userEmail, UserDTO userDTO) {
        return null;
    }

    private UserDTO mapUserToUserDTO(User user) {

        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());

        return userDTO;
    }
}
