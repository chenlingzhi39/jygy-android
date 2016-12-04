package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseResp;

/**
 * Created by wu on 15/12/2.
 */
public class GetAttendsResp extends BaseResp {

    private String type;//(1签到 2请假)
    private String attendate; //签到时间
    private String leaveEndDate;
    private String leaveStartDate;
    private String leaveReason;
    private String endTimeType;
    private String startTimeType;
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

	public String getLeaveReason() {
		return leaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

	public String getLeaveEndDate() {
		return leaveEndDate;
	}

	public void setLeaveEndDate(String leaveEndDate) {
		this.leaveEndDate = leaveEndDate;
	}

	public String getLeaveStartDate() {
		return leaveStartDate;
	}

	public void setLeaveStartDate(String leaveStartDate) {
		this.leaveStartDate = leaveStartDate;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttendate() {
        return attendate;
    }

    public void setAttendate(String attendate) {
        this.attendate = attendate;
    }
}
