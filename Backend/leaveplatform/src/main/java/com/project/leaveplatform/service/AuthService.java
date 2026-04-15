package com.project.leaveplatform.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.leaveplatform.dto.RegisterRequestDTO;
import com.project.leaveplatform.dto.LoginRequestDTO;
import com.project.leaveplatform.enums.Role;
import com.project.leaveplatform.entity.Stakeholder;
import com.project.leaveplatform.repo.StakeholderRepository;
import com.project.leaveplatform.security.JwtUtil;

@Service
public class AuthService {

    private final StakeholderRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(StakeholderRepository repo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // REGISTER
    public String register(RegisterRequestDTO request){

        Stakeholder user = new Stakeholder();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole()));
        user.setCourse(request.getCourse());

        repo.save(user);

        return "User Registered Successfully";
    }

    // LOGIN
    public String login(LoginRequestDTO request){

        Optional<Stakeholder> user = repo.findByEmail(request.getEmail());

        if(user.isPresent() && passwordEncoder.matches(request.getPassword(), user.get().getPassword())){
            String token = jwtUtil.generateToken(user.get().getEmail(), user.get().getRole().name());
            return "{\"token\":\"" + token + "\"}";
        }

        return "Invalid Credentials";
    }
}