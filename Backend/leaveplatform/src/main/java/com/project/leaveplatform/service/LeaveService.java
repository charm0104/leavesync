package com.project.leaveplatform.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project.leaveplatform.dto.LeaveApplicationResponseDTO;
import com.project.leaveplatform.entity.LeaveApplication;
import com.project.leaveplatform.entity.Stakeholder;
import com.project.leaveplatform.repo.LeaveRepository;
import com.project.leaveplatform.enums.LeaveStatus;
import com.project.leaveplatform.repo.StakeholderRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaveService {
    private final LeaveRepository leaveRepo;
    private final StakeholderRepository stakeholderRepo;

    public LeaveService(LeaveRepository leaveRepo, StakeholderRepository stakeholderRepo) {
    this.leaveRepo = leaveRepo;
    this.stakeholderRepo = stakeholderRepo;
}

public LeaveApplication applyLeave(LeaveApplication leave){

    // Date validation
    if (!leave.isUrgent() && leave.getFromDate().isBefore(LocalDate.now().plusDays(1))) {
        throw new IllegalArgumentException("Leave must be applied at least one day in advance");
    }

    // End date validation
    if (leave.getToDate().isBefore(leave.getFromDate())) {
        throw new IllegalArgumentException("End date cannot be before start date");
    }

    String email = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();

    Stakeholder user = stakeholderRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    leave.setFaculty(user);
    

    // Set default values
    leave.setStatus(LeaveStatus.PENDING);
    leave.setAppliedAt(LocalDateTime.now());

    // Save leave
    LeaveApplication savedLeave = leaveRepo.save(leave);

    // Simulate notification
    System.out.println("Notification sent to coordinator for leave ID: " + savedLeave.getId());

    return savedLeave;
}

    public List<LeaveApplication> getLeaveHistory(Long facultyId, LocalDateTime start, LocalDateTime end) {
        return leaveRepo.findByFaculty_IdAndAppliedAtBetween(facultyId, start, end);
    }

    public List<LeaveApplication> getAllLeaves() {
        return leaveRepo.findAll();
    }

    public List<LeaveApplication> getLeavesByStatus(LeaveStatus status) {
        return leaveRepo.findByStatus(status);
    }

    public List<LeaveApplication> getUrgentLeaves() {
        return leaveRepo.findByUrgentTrue();
    }

    public List<LeaveApplication> getLeavesByCourse(String course) {
        return leaveRepo.findByCourse(course);
    }

    public LeaveApplication approveLeave(Long id, String remarks) {

    LeaveApplication leave = leaveRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Leave not found"));

    leave.setStatus(LeaveStatus.APPROVED);
    leave.setRemarks(remarks);

    LeaveApplication updated = leaveRepo.save(leave);

    System.out.println("Approved leave notified to faculty & coordinator");

    return updated;
}

    public LeaveApplication rejectLeave(Long id, String remarks) {
        LeaveApplication leave = leaveRepo.findById(id).orElseThrow();
        leave.setStatus(LeaveStatus.REJECTED);
        leave.setRemarks(remarks);
        return leaveRepo.save(leave);
    }

    public List<LeaveApplicationResponseDTO> getAllLeavesDTO() {
    return leaveRepo.findAll()
            .stream()
            .map(this::toLeaveResponse)
            .toList();
}

public List<LeaveApplicationResponseDTO> getLeavesDTOByStatus(LeaveStatus status) {
    return leaveRepo.findByStatus(status)
            .stream()
            .map(this::toLeaveResponse)
            .toList();
}

public List<LeaveApplicationResponseDTO> getLeavesDTOByCourse(String course) {
    return leaveRepo.findByCourse(course)
            .stream()
            .map(this::toLeaveResponse)
            .toList();
}

public List<LeaveApplicationResponseDTO> getUrgentLeavesDTO() {
    return leaveRepo.findByUrgentTrue()
            .stream()
            .map(this::toLeaveResponse)
            .toList();
}

private LeaveApplicationResponseDTO toLeaveResponse(LeaveApplication leave) {

    return new LeaveApplicationResponseDTO(
            leave.getId(),
            leave.getReason(),
            leave.getStatus(),
            leave.getFromDate(),
            leave.getToDate(),
            leave.getLeaveType(),
            leave.isUrgent(),
            leave.getTimeSlot(),
            leave.getCourse(),
            leave.getRemarks(),
            leave.getFaculty() != null ? leave.getFaculty().getName() : null, 
            leave.getAppliedAt()
    );
}
}
