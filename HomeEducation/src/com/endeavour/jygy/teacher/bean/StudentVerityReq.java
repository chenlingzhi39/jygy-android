package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 16/1/13.
 */
public class StudentVerityReq extends BaseReq {
    @Override
    public String getUrl() {
        return "student/studentVerity";
    }

    private String studentId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
