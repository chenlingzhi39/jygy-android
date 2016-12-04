package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/16.
 */
public class GetTeacherTaskFeedbackListReq extends BaseReq {

    @Override
    public String getUrl() {
        return "student/findTaskRelays";
    }

    private String kinderId;
    private String classId;
    private String updateTime;

    public String getKinderId() {
        return kinderId;
    }

    public void setKinderId(String kinderId) {
        this.kinderId = kinderId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
