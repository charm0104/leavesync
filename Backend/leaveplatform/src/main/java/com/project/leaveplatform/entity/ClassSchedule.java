package com.project.leaveplatform.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "class_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "leave_id", nullable = false)
    private LeaveApplication leave;

    @ManyToOne
    @JoinColumn(name = "substitute_faculty_id", nullable = false)
    private Stakeholder substituteFaculty;

    @NotNull
    private LocalDate date;

    @NotBlank
    private String timeSlot;

    private String status; 

    private LocalDate fromDate;
    private LocalDate toDate;


}
