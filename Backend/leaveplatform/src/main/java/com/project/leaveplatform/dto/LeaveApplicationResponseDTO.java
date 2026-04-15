package com.project.leaveplatform.dto;

import com.project.leaveplatform.enums.LeaveStatus;
import com.project.leaveplatform.enums.LeaveType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApplicationResponseDTO {
    private Long id;
    private String reason;
    private LeaveStatus status;
    private LocalDate fromDate;
    private LocalDate toDate;
    private LeaveType leaveType;
    private boolean urgent;
    private String timeSlot;
    private String course;
    private String remarks;
    private String applicantName;
    private LocalDateTime appliedAt;
}
