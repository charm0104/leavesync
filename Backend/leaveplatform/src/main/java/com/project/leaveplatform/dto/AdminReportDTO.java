package com.project.leaveplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminReportDTO {
    private String facultyOnLeave;
    private String substituteFaculty;
    private String course;
    private LocalDate date;
    private String timeSlot;
}
