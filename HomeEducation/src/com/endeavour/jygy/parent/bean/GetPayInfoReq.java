package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 16/6/27.
 */
public class GetPayInfoReq extends BaseReq {

    @Override
    public String getUrl() {
        return "student/getKFeeChannelsInfos";
    }

    private String kinderId;
    private String studentId;

    public String getKinderId() {
        return kinderId;
    }

    public void setKinderId(String kinderId) {
        this.kinderId = kinderId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
