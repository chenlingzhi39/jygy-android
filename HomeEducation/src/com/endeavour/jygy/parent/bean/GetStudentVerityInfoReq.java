package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 16/1/27.
 */
public class GetStudentVerityInfoReq extends BaseReq {

    @Override
    public String getUrl() {
        return "student/getStudentVerityInfo";
    }

    /**
     * kinderId : 82
     * classId : 87
     * studentId : 236
     */

    private String kinderId;
    private String classId;
    private String studentId;

    public void setKinderId(String kinderId) {
        this.kinderId = kinderId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getKinderId() {
        return kinderId;
    }

    public String getClassId() {
        return classId;
    }

    public String getStudentId() {
        return studentId;
    }
}
