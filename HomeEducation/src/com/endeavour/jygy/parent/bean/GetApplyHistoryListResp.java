package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseResp;

/**
 * Created by wu on 16/7/6.
 */
public class GetApplyHistoryListResp extends BaseResp {

    private String id;
    private String status;
    private String gradeLevel;
    private String approvalTime;
    private String lastTime;
    private String createTime;
    private String applyUserName;
    private String kindergartenName;
    private String kindergartenClassName;
    private String newkindergartenClassName;
    private String approvalUserName;
    private String applyUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(String approvalTime) {
        this.approvalTime = approvalTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getKindergartenName() {
        return kindergartenName;
    }

    public void setKindergartenName(String kindergartenName) {
        this.kindergartenName = kindergartenName;
    }

    public String getKindergartenClassName() {
        return kindergartenClassName;
    }

    public void setKindergartenClassName(String kindergartenClassName) {
        this.kindergartenClassName = kindergartenClassName;
    }

    public String getNewkindergartenClassName() {
        return newkindergartenClassName;
    }

    public void setNewkindergartenClassName(String newkindergartenClassName) {
        this.newkindergartenClassName = newkindergartenClassName;
    }

    public String getApprovalUserName() {
        return approvalUserName;
    }

    public void setApprovalUserName(String approvalUserName) {
        this.approvalUserName = approvalUserName;
    }

    public String getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }
}
