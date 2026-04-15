package com.project.leaveplatform.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassScheduleResponseDTO {
    private Long id;
    private Long leaveId;
    private String replacementFacultyName;
    private String replacementFacultyEmail;
    private String course;
    private LocalDate date;
    private String timeSlot;
}
