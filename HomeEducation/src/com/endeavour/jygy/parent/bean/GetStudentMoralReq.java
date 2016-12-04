package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 16/1/27.
 */
public class GetStudentMoralReq extends BaseReq {


    private String kinderId;
    private String classId;
    private String studentId;
    private String gradeLevel;
    private String semester;

    @Override
    public String getUrl() {
        return "student/getStudentMoral";
    }


    public void setKinderId(String kinderId) {
        this.kinderId = kinderId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public void setSemester(String semester) {
        this.semester = semester;
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

    public String getGradeLevel() {
        return gradeLevel;
    }

    public String getSemester() {
        return semester;
    }
}
