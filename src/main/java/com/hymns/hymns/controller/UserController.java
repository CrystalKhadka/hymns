package com.hymns.hymns.controller;

import com.hymns.hymns.dto.UserDto;
import com.hymns.hymns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserDto userRequest) {
        if (userRequest == null) {
            return new ResponseEntity<>(Map.of("error", "Please provide user details"), HttpStatus.BAD_REQUEST);
        }
        // Validate user request fields
        if (userRequest.getEmail() == null || userRequest.getPassword() == null ||
                userRequest.getFirstName() == null || userRequest.getLastName() == null ||
                userRequest.getUsername() == null) {
            return new ResponseEntity<>(Map.of("error", "Please provide all required user details"), HttpStatus.BAD_REQUEST);
        }

        // Register user
        userService.register(userRequest);
        return new ResponseEntity<>(Map.of("message", "User registered successfully"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserDto userRequest) {
        if (userRequest == null) {
            return new ResponseEntity<>(Map.of("error", "Please provide user details"), HttpStatus.BAD_REQUEST);
        }
        // Validate user request fields
        if (userRequest.getEmail() == null || userRequest.getPassword() == null) {
            return new ResponseEntity<>(Map.of("error", "Please provide email and password"), HttpStatus.BAD_REQUEST);
        }

        // Call login service and get token
        String token;
        try {
            token = userService.login(userRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.UNAUTHORIZED);
        }

        // Return success response with token
        return new ResponseEntity<>(Map.of(
                "message", "User logged in successfully",
                "token", token
        ), HttpStatus.OK);
    }
}
