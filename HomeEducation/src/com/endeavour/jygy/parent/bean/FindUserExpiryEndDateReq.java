package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 16/7/20.
 */
public class FindUserExpiryEndDateReq extends BaseReq {

    @Override
    public String getUrl() {
        return "student/findUserExpiryEndDate";
    }

    private String studentId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
