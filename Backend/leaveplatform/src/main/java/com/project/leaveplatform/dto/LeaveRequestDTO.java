package com.project.leaveplatform.dto;

public class LeaveRequestDTO {

    private String startDate;
    private String endDate;
    private String leaveType;
    private String reason;
    private String leaveCategory;
    private String odPurpose;
    public String getStartDate() {
      return startDate;
    }
    public void setStartDate(String startDate) {
      this.startDate = startDate;
    }
    public String getEndDate() {
      return endDate;
    }
    public void setEndDate(String endDate) {
      this.endDate = endDate;
    }
    public String getLeaveType() {
      return leaveType;
    }
    public void setLeaveType(String leaveType) {
      this.leaveType = leaveType;
    }
    public String getReason() {
      return reason;
    }
    public void setReason(String reason) {
      this.reason = reason;
    }
    public String getLeaveCategory() {
      return leaveCategory;
    }
    public void setLeaveCategory(String leaveCategory) {
      this.leaveCategory = leaveCategory;
    }
    public String getOdPurpose() {
      return odPurpose;
    }
    public void setOdPurpose(String odPurpose) {
      this.odPurpose = odPurpose;
    }

}
