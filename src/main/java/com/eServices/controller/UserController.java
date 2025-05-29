package com.eServices.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eServices.dto.request.UserRegistrationRequest;
import com.eServices.dto.response.UserResponse;
import com.eServices.entity.User;
import com.eServices.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponse> userResponses = users.stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userResponses);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(u -> ResponseEntity.ok(convertToUserResponse(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/users/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        try {
            User user = convertToUser(request);
            User registeredUser = userService.registerUser(user);
            UserResponse response = convertToUserResponse(registeredUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRegistrationRequest request) {
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) {
            User user = convertToUser(request);
            user.setUserId(id);
            User updatedUser = userService.saveUser(user);
            UserResponse response = convertToUserResponse(updatedUser);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return user.map(u -> ResponseEntity.ok(convertToUserResponse(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Conversion methods
    private UserResponse convertToUserResponse(User user) {
        UserResponse.AddressResponse address = null;
        if (user.getStreet() != null || user.getCity() != null || user.getState() != null || 
            user.getZipCode() != null || user.getCountry() != null) {
            address = new UserResponse.AddressResponse(
                user.getStreet(),
                user.getCity(),
                user.getState(),
                user.getZipCode(),
                user.getCountry()
            );
        }

        return new UserResponse(
            user.getUserId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole(),
            address
        );
    }

    private User convertToUser(UserRegistrationRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getPassword()); // Will be hashed in service
        user.setRole(User.UserRole.valueOf(request.getRole().toUpperCase()));
        user.setStreet(request.getStreet());
        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setZipCode(request.getZipCode());
        user.setCountry(request.getCountry());
        return user;
    }
}
