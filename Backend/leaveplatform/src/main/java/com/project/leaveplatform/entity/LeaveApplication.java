package com.project.leaveplatform.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;
import com.project.leaveplatform.enums.LeaveStatus;
import com.project.leaveplatform.enums.LeaveType;

@Entity
@Table(name = "leave_application")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String reason;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    @NotNull
    private LocalDate fromDate;

    @NotNull
    private LocalDate toDate;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    private String odPurpose;

    private String documentPath;

    private boolean urgent = false;

    private String timeSlot;

    private String remarks;

    @Column(updatable = false)
    private LocalDateTime appliedAt;

    @Column
    private String course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "faculty_Id")
    private Stakeholder faculty;
}