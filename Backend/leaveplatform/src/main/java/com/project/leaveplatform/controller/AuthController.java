package com.project.leaveplatform.controller;

import org.springframework.web.bind.annotation.*;

import com.project.leaveplatform.dto.RegisterRequestDTO;
import com.project.leaveplatform.dto.LoginRequestDTO;
import com.project.leaveplatform.entity.Stakeholder;
import com.project.leaveplatform.service.AuthService;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request){
        return ResponseEntity.ok(authService.register(request));
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request){
        return ResponseEntity.ok(authService.login(request));
    }
}