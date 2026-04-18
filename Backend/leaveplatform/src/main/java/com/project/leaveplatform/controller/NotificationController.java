package com.project.leaveplatform.controller;

import com.project.leaveplatform.entity.Notification;
import com.project.leaveplatform.repo.NotificationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*") // allow frontend calls from any origin
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @GetMapping("/{facultyId}")
    public List<Notification> getNotifications(@PathVariable Long facultyId){
        return notificationRepository.findByFacultyIdOrderByCreatedAtDesc(facultyId);
    }

    @GetMapping("/count/{facultyId}")
    public long getUnreadCount(@PathVariable Long facultyId){
        return notificationRepository.countByFacultyIdAndIsReadFalse(facultyId);
    }

    @PutMapping("/read/{id}")
    public void markAsRead(@PathVariable Long id){
        Notification n = notificationRepository.findById(id).orElseThrow();
        n.setRead(true);
        notificationRepository.save(n);
    }
}
