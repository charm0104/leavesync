package com.project.leaveplatform.dto;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDTO {
    private Long leaveId;
    private Long replacementFacultyId;
    private LocalDate date;
    private String timeSlot;
}
