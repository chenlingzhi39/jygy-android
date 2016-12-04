package com.endeavour.jygy.parent.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wu on 16/9/18.
 */
public class RecommendContent implements Serializable {


    private String stuName;
    private String className;
    private String lessonPlanTitle;
    private String taskContent;
    private String completeContent;
    private List<String> completeAttachement;
    private String teacherId;

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getLessonPlanTitle() {
        return lessonPlanTitle;
    }

    public void setLessonPlanTitle(String lessonPlanTitle) {
        this.lessonPlanTitle = lessonPlanTitle;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getCompleteContent() {
        return completeContent;
    }

    public void setCompleteContent(String completeContent) {
        this.completeContent = completeContent;
    }

    public List<String> getCompleteAttachement() {
        return completeAttachement;
    }

    public void setCompleteAttachement(List<String> completeAttachement) {
        this.completeAttachement = completeAttachement;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}
