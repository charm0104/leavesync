package com.project.leaveplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardDTO {
    private long totalNotifications;
    private long pendingAdjustments;
    private long completedAdjustments;
    private long urgentLeaves;
}
