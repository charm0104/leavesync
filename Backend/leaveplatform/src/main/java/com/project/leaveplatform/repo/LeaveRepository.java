package com.project.leaveplatform.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.leaveplatform.entity.LeaveApplication;
import com.project.leaveplatform.enums.LeaveStatus;

public interface LeaveRepository extends JpaRepository<LeaveApplication, Long> {

    List<LeaveApplication> findAll();

    List<LeaveApplication> findByStatus(LeaveStatus status);

    List<LeaveApplication> findByUrgentTrue();

    List<LeaveApplication> findByFaculty_Id(Long facultyId);

    List<LeaveApplication> findByCourse(String course);

    List<LeaveApplication> findByFromDate(LocalDate fromDate);

    List<LeaveApplication> findByTimeSlot(String timeSlot);

    List<LeaveApplication> findByCourseAndFromDate(String course, LocalDate fromDate);

    List<LeaveApplication> findByCourseAndTimeSlot(String course, String timeSlot);

    List<LeaveApplication> findByFromDateAndTimeSlot(LocalDate fromDate, String timeSlot);

    List<LeaveApplication> findByCourseAndFromDateAndTimeSlot(String course, LocalDate fromDate, String timeSlot);

    List<LeaveApplication> findByFaculty_IdAndAppliedAtBetween(Long facultyId, LocalDateTime start, LocalDateTime end);

    long countByStatus(LeaveStatus pending);

    long countByUrgentTrue();

}
