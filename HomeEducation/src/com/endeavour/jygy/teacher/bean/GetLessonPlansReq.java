package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/14.
 */

public class GetLessonPlansReq extends BaseReq {

    @Override
    public String getUrl() {
        return "student/getLessonPlans";
    }

    private String gradeLevel;
    private String semester;
    private String gradeId;
    private String classId;
    private String updateTime;

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

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

}
