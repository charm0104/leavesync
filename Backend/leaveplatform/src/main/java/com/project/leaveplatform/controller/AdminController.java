package com.project.leaveplatform.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.leaveplatform.dto.AdminDashboardDTO;
import com.project.leaveplatform.dto.AdminReportDTO;
import com.project.leaveplatform.dto.ClassScheduleResponseDTO;
import com.project.leaveplatform.dto.FacultyDTO;
import com.project.leaveplatform.dto.LeaveNotificationDTO;
import com.project.leaveplatform.dto.LeaveApplicationResponseDTO;
import com.project.leaveplatform.dto.ScheduleRequestDTO;
import com.project.leaveplatform.dto.ScheduleResponseDTO;
import com.project.leaveplatform.enums.LeaveStatus;
import com.project.leaveplatform.service.AdminService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/approved-leaves")
    public ResponseEntity<List<LeaveApplicationResponseDTO>> getApprovedLeaves() {
        return ResponseEntity.ok(adminService.getApprovedLeaves());
    }

    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardDTO> getDashboardSummary() {
        return ResponseEntity.ok(adminService.getDashboardSummary());
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<LeaveNotificationDTO>> getNotifications(
            @RequestParam(required = false) String course,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String timeSlot) {
        return ResponseEntity.ok(adminService.getNotifications(course, date, timeSlot));
    }

    @PostMapping("/schedule")
    public ResponseEntity<ScheduleResponseDTO> scheduleClass(@RequestBody ScheduleRequestDTO request) {
        return ResponseEntity.ok(adminService.scheduleClass(request));
    }

    @GetMapping("/available-faculty")
    public ResponseEntity<List<FacultyDTO>> getAvailableFaculty(
            @RequestParam(required = false) String course,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String timeSlot) {
        return ResponseEntity.ok(adminService.getAvailableFaculty(course, date, timeSlot));
    }

    @GetMapping("/reports")
    public ResponseEntity<List<AdminReportDTO>> getReports(
            @RequestParam(required = false) String course,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(adminService.getReports(course, date));
    }

    @GetMapping("/history")
    public ResponseEntity<List<ClassScheduleResponseDTO>> getScheduleHistory() {
        return ResponseEntity.ok(adminService.getScheduleHistory());
    }

    @GetMapping("/urgent")
    public ResponseEntity<List<LeaveApplicationResponseDTO>> getUrgentLeaves() {
    return ResponseEntity.ok(adminService.getUrgentLeaves());

}

    @PutMapping("/cancel/{scheduleId}")
public ResponseEntity<?> cancelSchedule(@PathVariable Long scheduleId) {
    adminService.cancelSchedule(scheduleId);
    return ResponseEntity.ok("Cancelled successfully");
}
}
