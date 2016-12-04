package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 16/7/6.
 */
public class AdjustClassApplyReq extends BaseReq{

    @Override
    public String getUrl() {
        return "student/adjustClassApply";
    }

    private String kinderId;
    private String classId;
    private String newClassId;
    private String studengId;

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

    public String getNewClassId() {
        return newClassId;
    }

    public void setNewClassId(String newClassId) {
        this.newClassId = newClassId;
    }

    public String getStudengId() {
        return studengId;
    }

    public void setStudengId(String studengId) {
        this.studengId = studengId;
    }
}
