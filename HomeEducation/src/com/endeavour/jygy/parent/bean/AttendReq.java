package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/2.
 */
public class AttendReq extends BaseReq {

    @Override
    public String getUrl() {
        return "parent/attend";
    }

    private String attendDate; //签到日期
    private String parentId; //家长ID
    private String studentId; //学生ID
    private String classId; //班级ID
    private String attendType; //1签到 2请假
    private String leaveStartDate;
    private String leaveEndDate;
    private String leaveReason;
    private String startTimeType;
    private String endTimeType;

    public String getStartTimeType() {
        return startTimeType;
    }

    public void setStartTimeType(String startTimeType) {
        this.startTimeType = startTimeType;
    }

    public String getEndTimeType() {
        return endTimeType;
    }

    public void setEndTimeType(String endTimeType) {
        this.endTimeType = endTimeType;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public String getAttendDate() {
        return attendDate;
    }

    public void setAttendDate(String attendDate) {
        this.attendDate = attendDate;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getAttendType() {
        return attendType;
    }

    public void setAttendType(String attendType) {
        this.attendType = attendType;
    }

    public String getLeaveStartDate() {
        return leaveStartDate;
    }

    public void setLeaveStartDate(String leaveStartDate) {
        this.leaveStartDate = leaveStartDate;
    }

    public String getLeaveEndDate() {
        return leaveEndDate;
    }

    public void setLeaveEndDate(String leaveEndDate) {
        this.leaveEndDate = leaveEndDate;
    }
}
