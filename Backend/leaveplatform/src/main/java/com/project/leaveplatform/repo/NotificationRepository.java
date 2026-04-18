package com.project.leaveplatform.repo;

import com.project.leaveplatform.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByFacultyIdOrderByCreatedAtDesc(Long facultyId);

    long countByFacultyIdAndIsReadFalse(Long facultyId);
}
