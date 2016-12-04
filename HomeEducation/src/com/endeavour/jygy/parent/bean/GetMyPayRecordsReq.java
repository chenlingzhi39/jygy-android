package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 16/6/29.
 */
public class GetMyPayRecordsReq extends BaseReq {

    @Override
    public String getUrl() {
        return "student/getMyPayRecords";
    }

    private String studentId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
