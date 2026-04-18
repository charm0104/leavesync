package com.project.leaveplatform.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private boolean isRead = false;

    private Long facultyId;

    private Long leaveId;

    private String status; // APPROVED / REJECTED

    private String remarks;

    public Long getId() {
      return id;
}

    public void setId(Long id) {
      this.id = id;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public boolean isRead() {
      return isRead;
    }

    public void setRead(boolean isRead) {
      this.isRead = isRead;
    }

    public Long getFacultyId() {
      return facultyId;
    }

    public void setFacultyId(Long facultyId) {
      this.facultyId = facultyId;
    }

    public Long getLeaveId() {
      return leaveId;
    }

    public void setLeaveId(Long leaveId) {
      this.leaveId = leaveId;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getRemarks() {
      return remarks;
    }

    public void setRemarks(String remarks) {
      this.remarks = remarks;
    }

    public LocalDateTime getCreatedAt() {
      return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
    }

    private LocalDateTime createdAt = LocalDateTime.now();

    // getters & setters

    
}


