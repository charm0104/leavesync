package com.project.leaveplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.leaveplatform.entity.Stakeholder;
import com.project.leaveplatform.repo.StakeholderRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin
public class StakeholderController {

@Autowired
private StakeholderRepository stakeholderRepo;
@GetMapping("/user/me")
public Stakeholder getLoggedInUser() {
    String email = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();

    return stakeholderRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
}

}
