package com.darin.taskmanager.controller;

import com.darin.taskmanager.dto.ApiResponse;
import com.darin.taskmanager.dto.RegistrationLoginRequest;
import com.darin.taskmanager.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody RegistrationLoginRequest registrationRequest) {
        return ResponseEntity.ok(authenticationService.register(registrationRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody RegistrationLoginRequest loginRequest, HttpServletRequest request) {
        return ResponseEntity.ok(authenticationService.login(loginRequest, request));
    }
}
