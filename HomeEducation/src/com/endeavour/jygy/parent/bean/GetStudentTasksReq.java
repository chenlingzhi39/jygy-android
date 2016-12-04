package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/14.
 */
public class GetStudentTasksReq extends BaseReq {

    @Override
    public String getUrl() {
        return "parent/getStudentTasks";
    }

    private String kinderId;
    private String classId;
    private String studentId;
    private String pageNo;
    private String perNo;
    private String gradeLevel;
    private String semester;

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPerNo() {
        return perNo;
    }

    public void setPerNo(String perNo) {
        this.perNo = perNo;
    }
}
