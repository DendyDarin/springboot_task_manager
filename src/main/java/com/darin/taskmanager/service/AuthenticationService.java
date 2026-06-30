package com.darin.taskmanager.service;

import com.darin.taskmanager.dto.ApiResponse;
import com.darin.taskmanager.dto.RegistrationLoginRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    ApiResponse<?> register(RegistrationLoginRequest registerRequest);
    ApiResponse<?> login(RegistrationLoginRequest loginRequest, HttpServletRequest request);
}
