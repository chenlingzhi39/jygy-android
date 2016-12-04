package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/19.
 */
public class GetParentLessonReq extends BaseReq {

    @Override
    public String getUrl() {
        return "lesson/getPractices";
    }

    private String studentId;
    private String kinderId;
    private String classId;
    private String gradeLevel;
    private String semester;
    private String lessonPlanId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public String getLessonPlanId() {
        return lessonPlanId;
    }

    public void setLessonPlanId(String lessonPlanId) {
        this.lessonPlanId = lessonPlanId;
    }
}
