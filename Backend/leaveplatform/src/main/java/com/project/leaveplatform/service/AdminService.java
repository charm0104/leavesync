package com.project.leaveplatform.service;

import org.springframework.stereotype.Service;
import com.project.leaveplatform.dto.AdminDashboardDTO;
import com.project.leaveplatform.dto.AdminReportDTO;
import com.project.leaveplatform.dto.ClassScheduleResponseDTO;
import com.project.leaveplatform.dto.FacultyDTO;
import com.project.leaveplatform.dto.LeaveApplicationResponseDTO;
import com.project.leaveplatform.dto.LeaveNotificationDTO;
import com.project.leaveplatform.dto.ScheduleRequestDTO;
import com.project.leaveplatform.dto.ScheduleResponseDTO;
import com.project.leaveplatform.entity.ClassSchedule;
import com.project.leaveplatform.entity.LeaveApplication;
import com.project.leaveplatform.entity.Stakeholder;
import com.project.leaveplatform.enums.LeaveStatus;
import com.project.leaveplatform.enums.Role;
import com.project.leaveplatform.exception.ResourceNotFoundException;
import com.project.leaveplatform.exception.SchedulingConflictException;
import com.project.leaveplatform.repo.ClassScheduleRepository;
import com.project.leaveplatform.repo.LeaveRepository;
import com.project.leaveplatform.repo.StakeholderRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final LeaveRepository leaveRepo;
    private final StakeholderRepository stakeholderRepo;
    private final ClassScheduleRepository scheduleRepo;

    public AdminService(LeaveRepository leaveRepo,
                        StakeholderRepository stakeholderRepo,
                        ClassScheduleRepository scheduleRepo) {
        this.leaveRepo = leaveRepo;
        this.stakeholderRepo = stakeholderRepo;
        this.scheduleRepo = scheduleRepo;
    }

    public AdminDashboardDTO getDashboardSummary() {
        long totalLeaves = leaveRepo.count();
        long pendingAdjustments = leaveRepo.countByStatus(LeaveStatus.PENDING);
        long completedAdjustments = leaveRepo.countByStatus(LeaveStatus.APPROVED);
        long urgentLeaves = leaveRepo.countByUrgentTrue();

        return new AdminDashboardDTO(totalLeaves, pendingAdjustments, completedAdjustments, urgentLeaves);
    }

    public List<LeaveApplicationResponseDTO> getApprovedLeaves() {
        return leaveRepo.findByStatus(LeaveStatus.APPROVED)
                .stream()
                .map(this::toLeaveResponse)
                .collect(Collectors.toList());
    }

    public List<LeaveApplicationResponseDTO> getUrgentLeaves() {
    return leaveRepo.findByUrgentTrue()
            .stream()
            .map(this::toLeaveResponse)
            .toList();
}

public void cancelSchedule(Long id) {
    ClassSchedule schedule = scheduleRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Schedule not found"));

    schedule.setStatus("CANCELLED");
    scheduleRepo.save(schedule);
}

    public List<LeaveNotificationDTO> getNotifications(String course, LocalDate date, String timeSlot) {
        List<LeaveApplication> leaves = getFilteredLeaves(course, date, timeSlot);
        return leaves.stream()
                .sorted(Comparator.comparing(LeaveApplication::getAppliedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .map(this::toNotificationDto)
                .collect(Collectors.toList());
    }

    public ScheduleResponseDTO scheduleClass(ScheduleRequestDTO request) {
        LeaveApplication leave = leaveRepo.findById(request.getLeaveId())
                .orElseThrow(() -> new ResourceNotFoundException("Leave application not found: " + request.getLeaveId()));

        if (!LeaveStatus.APPROVED.equals(leave.getStatus())) {
            throw new IllegalArgumentException("Only approved leaves can be scheduled");
        }

        Stakeholder replacementFaculty = stakeholderRepo.findById(request.getReplacementFacultyId())
                .orElseThrow(() -> new ResourceNotFoundException("Replacement faculty not found: " + request.getReplacementFacultyId()));

        if (!Role.FACULTY.equals(replacementFaculty.getRole())) {
            throw new IllegalArgumentException("Replacement user must have FACULTY role");
        }

        if (scheduleRepo.existsByLeave_Id(request.getLeaveId())) {
            throw new SchedulingConflictException("A schedule already exists for leave ID " + request.getLeaveId());
        }

        if (scheduleRepo.existsByDateAndTimeSlotAndSubstituteFacultyId(request.getDate(), request.getTimeSlot(), replacementFaculty.getId())) {
            throw new SchedulingConflictException("Faculty is already assigned for the selected date and time slot");
        }

        ClassSchedule schedule = new ClassSchedule();
        schedule.setLeave(leave);
        schedule.setSubstituteFaculty(replacementFaculty);
        schedule.setDate(request.getDate());
        schedule.setTimeSlot(request.getTimeSlot());
        schedule.setStatus("ASSIGNED");
        scheduleRepo.save(schedule);

        return new ScheduleResponseDTO("Class scheduled successfully", "SUCCESS");
    }

    public List<FacultyDTO> getAvailableFaculty(String course, LocalDate date, String timeSlot) {
        List<Stakeholder> faculty = stakeholderRepo.findByRole(Role.FACULTY);

        Set<Long> unavailableFacultyIds = new HashSet<>();
        if (date != null) {
            unavailableFacultyIds.addAll(leaveRepo.findAll()
                    .stream()
                    .filter(leave -> leave.getFaculty() != null)
                    .filter(leave -> leave.getStatus() == LeaveStatus.APPROVED || leave.isUrgent())
                    .filter(leave -> leave.getFromDate() != null && leave.getToDate() != null)
                    .filter(leave -> !date.isBefore(leave.getFromDate()) && !date.isAfter(leave.getToDate()))
                    .map(leave -> leave.getFaculty().getId())
                    .collect(Collectors.toSet()));
        }

        Set<Long> assignedFacultyIds = new HashSet<>();
        if (date != null && timeSlot != null && !timeSlot.isBlank()) {
            assignedFacultyIds.addAll(scheduleRepo.findByDateAndTimeSlot(date, timeSlot)
                    .stream()
                    .map(schedule -> schedule.getSubstituteFaculty().getId())
                    .collect(Collectors.toSet()));
        }

        return faculty.stream()
                .filter(f -> !unavailableFacultyIds.contains(f.getId()))
                .filter(f -> !assignedFacultyIds.contains(f.getId()))
                .filter(f -> course == null || course.isBlank() || course.equalsIgnoreCase(f.getCourse()))
                .map(this::toFacultyDto)
                .toList();
    }

    public List<AdminReportDTO> getReports(String course, LocalDate date) {
        return scheduleRepo.findAll().stream()
                .filter(schedule -> course == null || course.isBlank() || course.equalsIgnoreCase(schedule.getLeave().getCourse()))
                .filter(schedule -> date == null || date.equals(schedule.getDate()))
                .map(this::toReportDto)
                .collect(Collectors.toList());
    }

    public List<ClassScheduleResponseDTO> getScheduleHistory() {
        return scheduleRepo.findAll()
                .stream()
                .map(this::toScheduleResponse)
                .collect(Collectors.toList());
    }

    private List<LeaveApplication> getFilteredLeaves(String course, LocalDate date, String timeSlot) {
        if (course != null && !course.isBlank() && date != null && timeSlot != null && !timeSlot.isBlank()) {
            return leaveRepo.findByCourseAndFromDateAndTimeSlot(course, date, timeSlot);
        }
        if (course != null && !course.isBlank() && date != null) {
            return leaveRepo.findByCourseAndFromDate(course, date);
        }
        if (course != null && !course.isBlank() && timeSlot != null && !timeSlot.isBlank()) {
            return leaveRepo.findByCourseAndTimeSlot(course, timeSlot);
        }
        if (date != null && timeSlot != null && !timeSlot.isBlank()) {
            return leaveRepo.findByFromDateAndTimeSlot(date, timeSlot);
        }
        if (course != null && !course.isBlank()) {
            return leaveRepo.findByCourse(course);
        }
        if (date != null) {
            return leaveRepo.findByFromDate(date);
        }
        if (timeSlot != null && !timeSlot.isBlank()) {
            return leaveRepo.findByTimeSlot(timeSlot);
        }
        return leaveRepo.findAll();
    }

    private LeaveNotificationDTO toNotificationDto(LeaveApplication leave) {
        return new LeaveNotificationDTO(
                leave.getId(),
                leave.getFaculty() != null ? leave.getFaculty().getName() : null,
                leave.getCourse(),
                leave.getFromDate(),
                leave.getToDate(),
                leave.getTimeSlot(),
                leave.getStatus()
        );
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

    private FacultyDTO toFacultyDto(Stakeholder stakeholder) {
        return new FacultyDTO(
                stakeholder.getId(),
                stakeholder.getName(),
                stakeholder.getEmail(),
                stakeholder.getCourse() != null ? stakeholder.getCourse() : null
        );
    }

    private ClassScheduleResponseDTO toScheduleResponse(ClassSchedule schedule) {
        return new ClassScheduleResponseDTO(
                schedule.getId(),
                schedule.getLeave().getId(),
                schedule.getSubstituteFaculty().getName(),
                schedule.getSubstituteFaculty().getEmail(),
                schedule.getLeave().getCourse(),
                schedule.getDate(),
                schedule.getTimeSlot()
        );
    }

    private AdminReportDTO toReportDto(ClassSchedule schedule) {
        String facultyOnLeave = schedule.getLeave().getFaculty() != null ? schedule.getLeave().getFaculty().getName() : null;
        String substituteFaculty = schedule.getSubstituteFaculty() != null ? schedule.getSubstituteFaculty().getName() : null;
        return new AdminReportDTO(
                facultyOnLeave,
                substituteFaculty,
                schedule.getLeave().getCourse(),
                schedule.getDate(),
                schedule.getTimeSlot()
        );
    }


}
