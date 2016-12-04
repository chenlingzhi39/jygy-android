package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseResp;

import java.util.List;

/**
 * Created by wu on 15/12/14.
 */
public class GetLessonPlanResp extends BaseResp {

    private String content;
    private String id;
    private String createTime;
    private String title;
    private String validFlag;
    private String updateTime;
    private String serialNumber;
    private String gradeLevel;
    private String semester;
    private String dupSend;
    private List<TeacherPlanDetailTask> tasks;
    private List<TeacherPlanDetailUserPractices> userPractices;

    public String getDupSend() {
        return dupSend;
    }

    public void setDupSend(String dupSend) {
        this.dupSend = dupSend;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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

    public List<TeacherPlanDetailTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<TeacherPlanDetailTask> tasks) {
        this.tasks = tasks;
    }

    public List<TeacherPlanDetailUserPractices> getUserPractices() {
        return userPractices;
    }

    public void setUserPractices(List<TeacherPlanDetailUserPractices> userPractices) {
        this.userPractices = userPractices;
    }
}
