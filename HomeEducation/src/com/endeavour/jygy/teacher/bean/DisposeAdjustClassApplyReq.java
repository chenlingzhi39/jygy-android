package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 16/7/6.
 */
public class DisposeAdjustClassApplyReq extends BaseReq {

    @Override
    public String getUrl() {
        return "student/disposeAdjustClassApply";
    }

    private String classApplyId;
    private String approvalStatus;
    private String approvalUserId;

    public String getClassApplyId() {
        return classApplyId;
    }

    public void setClassApplyId(String classApplyId) {
        this.classApplyId = classApplyId;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalUserId() {
        return approvalUserId;
    }

    public void setApprovalUserId(String approvalUserId) {
        this.approvalUserId = approvalUserId;
    }
}
