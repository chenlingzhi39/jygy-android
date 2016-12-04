package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseResp;

/**
 * Created by wu on 16/1/13.
 */
public class StudentVerityInfosResp extends BaseResp {

    private String studentId;
    private String studentName;
    private String parentName;
    private String validFlag;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }
}
