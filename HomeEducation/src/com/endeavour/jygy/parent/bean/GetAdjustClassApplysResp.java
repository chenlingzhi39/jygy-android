package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseResp;

/**
 * Created by wu on 16/7/11.
 */
public class GetAdjustClassApplysResp extends BaseResp {


    /**
     * id : 7
     * status : 0
     * gradeLevel : 3
     * lastTime : 2016-07-06 20:40:14
     * createTime : 2016-07-06 20:40:14
     * applyUserName : 张亦宸
     * kindergartenName : 儒灵童幼稚园
     * kindergartenClassName : 中一班
     * newkindergartenClassName : 中二班
     * applyUserId : 574
     */

    private String id;
    private String status;
    private String gradeLevel;
    private String lastTime;
    private String createTime;
    private String applyUserName;
    private String kindergartenName;
    private String kindergartenClassName;
    private String newkindergartenClassName;
    private int applyUserId;

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

    public int getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(int applyUserId) {
        this.applyUserId = applyUserId;
    }
}
