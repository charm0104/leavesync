package com.project.leaveplatform.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.leaveplatform.entity.ClassSchedule;
import java.time.LocalDate;
import java.util.List;

public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
    List<ClassSchedule> findByDateAndTimeSlot(LocalDate date, String timeSlot);
    boolean existsByDateAndTimeSlotAndSubstituteFacultyId(LocalDate date, String timeSlot, Long substituteFacultyId);
    boolean existsByLeave_Id(Long leaveId);

    List<ClassSchedule> findByDate(LocalDate date);
}
