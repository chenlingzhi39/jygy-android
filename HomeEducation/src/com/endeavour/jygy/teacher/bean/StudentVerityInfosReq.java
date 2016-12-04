package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 16/1/13.
 */
public class StudentVerityInfosReq extends BaseReq {

    @Override
    public String getUrl() {
        return "student/studentVerityInfos";
    }

    private String kinderId;
    private String classId;

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
}
