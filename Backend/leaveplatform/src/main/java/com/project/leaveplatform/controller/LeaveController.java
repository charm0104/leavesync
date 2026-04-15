package com.project.leaveplatform.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import com.project.leaveplatform.dto.ApprovalDTO;
import com.project.leaveplatform.dto.LeaveApplicationResponseDTO;
import com.project.leaveplatform.entity.LeaveApplication;
import com.project.leaveplatform.entity.Stakeholder;
import com.project.leaveplatform.service.LeaveService;
import com.project.leaveplatform.enums.LeaveStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.leaveplatform.repo.StakeholderRepository;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;
    private final ObjectMapper objectMapper;
    private final StakeholderRepository stakeholderRepo;

    public LeaveController(LeaveService leaveService, ObjectMapper objectMapper, StakeholderRepository stakeholderRepo){
        this.leaveService = leaveService;
        this.objectMapper = objectMapper;
        this.stakeholderRepo = stakeholderRepo;
    }

    @PostMapping(value = "/apply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<?> applyLeave(
        @RequestParam("leave") String leaveJson,
        @RequestParam(value = "file", required = false) MultipartFile file) {

    try {
        LeaveApplication leave = objectMapper.readValue(leaveJson, LeaveApplication.class);


String email = SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getName();

Stakeholder user = stakeholderRepo.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

leave.setFaculty(user);         
leave.setCourse(user.getCourse());  

        if (file != null && !file.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(System.getProperty("user.dir"), "uploads", fileName);

            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            leave.setDocumentPath(path.toString());
        }

        LeaveApplication saved = leaveService.applyLeave(leave);

        return ResponseEntity.ok(saved);

    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
}

    @PostMapping("/apply-urgent")
    public LeaveApplication applyUrgentLeave(@RequestBody LeaveApplication leave){

        String email = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();

    Stakeholder user = stakeholderRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    leave.setFaculty(user);             
    leave.setCourse(user.getCourse());   

        leave.setUrgent(true);
        return leaveService.applyLeave(leave);
    }


    @GetMapping("/history/{facultyId}")
    public List<LeaveApplication> getLeaveHistory(@PathVariable Long facultyId, @RequestParam(required = false) String period) {
        LocalDateTime start = LocalDateTime.now().minusDays(30); // default last 30 days
        LocalDateTime end = LocalDateTime.now();
        if ("weekly".equals(period)) {
            start = LocalDateTime.now().minusWeeks(1);
        } else if ("monthly".equals(period)) {
            start = LocalDateTime.now().minusMonths(1);
        } else if ("yearly".equals(period)) {
            start = LocalDateTime.now().minusYears(1);
        }
        // for custom, assume params, but simple
        return leaveService.getLeaveHistory(facultyId, start, end);
    }

    @GetMapping("/all")
    public List<LeaveApplicationResponseDTO> getAllLeaves(@RequestParam(required = false) String status, @RequestParam(required = false) String course) {
        if (status != null) {
            try {
                LeaveStatus leaveStatus = LeaveStatus.valueOf(status.toUpperCase());
                return leaveService.getLeavesDTOByStatus(leaveStatus);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status: " + status);
            }
        } else if (course != null) {
            return leaveService.getLeavesDTOByCourse(course);
        }
        return leaveService.getAllLeavesDTO();
    }

    @GetMapping("/urgent")
    public List<LeaveApplicationResponseDTO> getUrgentLeaves() {
        return leaveService.getUrgentLeavesDTO();
    }

    @PutMapping("/approve/{id}")
public ResponseEntity<?> approveLeave(@PathVariable Long id, @RequestBody ApprovalDTO request) {
    return ResponseEntity.ok(leaveService.approveLeave(id, request.getRemarks()));
}

    @PutMapping("/reject/{id}")
public ResponseEntity<?> rejectLeave(@PathVariable Long id, @RequestBody ApprovalDTO request) {
    return ResponseEntity.ok(leaveService.rejectLeave(id, request.getRemarks()));
}
}