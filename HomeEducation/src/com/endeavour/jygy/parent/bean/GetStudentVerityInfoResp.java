package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseResp;

/**
 * Created by wu on 16/1/27.
 */
public class GetStudentVerityInfoResp extends BaseResp {


    /**
     * studentId : 236
     * validFlag : 1
     */

    private int studentId;
    private String validFlag;

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getValidFlag() {
        return validFlag;
    }
}
