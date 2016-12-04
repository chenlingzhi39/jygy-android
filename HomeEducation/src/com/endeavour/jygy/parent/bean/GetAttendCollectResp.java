package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseResp;

public class GetAttendCollectResp extends BaseResp {
    private int attendId;
    private String attendDays;
    private long endDate;
    private long startDate;
    private String userName;
    private long createTime;
    private String reason;
    private String endTimeType;
    private String startTimeType;

    public int getAttendId() {
        return attendId;
    }

    public void setAttendId(int attendId) {
        this.attendId = attendId;
    }

    public String getAttendDays() {
        return attendDays;
    }

    public void setAttendDays(String attendDays) {
        this.attendDays = attendDays;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getEndTimeType() {
        return endTimeType;
    }

    public void setEndTimeType(String endTimeType) {
        this.endTimeType = endTimeType;
    }

    public String getStartTimeType() {
        return startTimeType;
    }

    public void setStartTimeType(String startTimeType) {
        this.startTimeType = startTimeType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

}
