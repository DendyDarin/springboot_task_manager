package com.darin.taskmanager.service.implementation;

import com.darin.taskmanager.dto.ApiResponse;
import com.darin.taskmanager.dto.RegistrationLoginRequest;
import com.darin.taskmanager.dto.UserDTO;
import com.darin.taskmanager.enums.Role;
import com.darin.taskmanager.exception.BadRequestException;
import com.darin.taskmanager.model.User;
import com.darin.taskmanager.repository.UserRepository;
import com.darin.taskmanager.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        // check if email already exist
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exist");
        }

        // if it's not then make new User Object with its email and password
        User newUser = new User();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // check the role
        if (registerRequest.getRole() == null) {
            newUser.setRole(Role.USER);
        } else if (registerRequest.getRole().equals(Role.ADMIN)) {
            newUser.setRole(Role.ADMIN);
        } else {
            newUser.setRole(Role.USER);
        }

        // Return response
        User savedNewUser = userRepository.save(newUser);

        UserDTO savedNewUserDTO = new UserDTO();
        savedNewUserDTO.setEmail(savedNewUser.getEmail());
        savedNewUserDTO.setRole(savedNewUser.getRole());

        return new ApiResponse<>(201, "New User has been saved successfully", savedNewUserDTO);
    }

    @Override
    public ApiResponse<?> login(RegistrationLoginRequest loginRequest, HttpServletRequest request) {

        // Check if email and password exist
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            )
        );

        // Save info to security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Create cookie session (JSESSIONID)
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        // Return response
        return new ApiResponse<>(200, "Login successfully", null);
    }
}
