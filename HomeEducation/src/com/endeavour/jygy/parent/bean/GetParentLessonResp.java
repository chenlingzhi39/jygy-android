package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseResp;

import java.util.List;

/**
 * Created by wu on 15/12/19.
 */
public class GetParentLessonResp extends BaseResp {

    private String planName;
    private String sNumber;
    private String userName;
    private String gradeLevel;
    private String planId;
    private String invalidDate;
    private String semester;
    private List<GetParentLessonPracticeDet> practiceDet;

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getsNumber() {
        return sNumber;
    }

    public void setsNumber(String sNumber) {
        this.sNumber = sNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(String invalidDate) {
        this.invalidDate = invalidDate;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public List<GetParentLessonPracticeDet> getPracticeDet() {
        return practiceDet;
    }

    public void setPracticeDet(List<GetParentLessonPracticeDet> practiceDet) {
        this.practiceDet = practiceDet;
    }
}
