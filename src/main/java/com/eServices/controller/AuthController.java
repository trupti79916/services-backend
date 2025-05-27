package com.eServices.controller;

import com.eServices.dto.request.LoginRequest;
import com.eServices.dto.response.JwtResponse;
import com.eServices.entity.User;
import com.eServices.repository.UserRepository;
import com.eServices.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
                String jwt = jwtUtils.generateJwtToken(user.getUsername());
                return ResponseEntity.ok(new JwtResponse(jwt, user.getUsername()));
            }
        }
        return ResponseEntity.badRequest().body("Invalid username or password");
    }
}
