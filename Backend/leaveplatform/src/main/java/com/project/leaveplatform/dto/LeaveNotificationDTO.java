package com.project.leaveplatform.dto;

import com.project.leaveplatform.enums.LeaveStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveNotificationDTO {
    private Long id;
    private String facultyName;
    private String course;
    private LocalDate startDate;
    private LocalDate endDate;
    private String timeSlot;
    private LeaveStatus status;
}
