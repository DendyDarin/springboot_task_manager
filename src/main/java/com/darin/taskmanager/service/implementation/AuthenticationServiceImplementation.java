package com.darin.taskmanager.service.implementation;

import com.darin.taskmanager.dto.ApiResponse;
import com.darin.taskmanager.dto.RegistrationLoginRequest;
import com.darin.taskmanager.enums.Role;
import com.darin.taskmanager.exception.BadRequestException;
import com.darin.taskmanager.model.User;
import com.darin.taskmanager.repository.UserRepository;
import com.darin.taskmanager.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImplementation implements AuthenticationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public ApiResponse<?> register(RegistrationLoginRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exist");
        }
        User newUser = new User();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        if (registerRequest.getRole().equals(Role.ADMIN)) {
            newUser.setRole(Role.ADMIN);
        } else {
            newUser.setRole(Role.USER);
        }
        userRepository.save(newUser);
        return new ApiResponse<>(201, "New User has been saved successfully", null);
    }

    @Override
    public ApiResponse<?> login(RegistrationLoginRequest loginRequest, HttpServletRequest request) {
        return null;
    }
}
