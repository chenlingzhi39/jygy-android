package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/17.
 */
public class ReplyStudentTasksReq extends BaseReq {

    @Override
    public String getUrl() {
        return "parent/replyStudentTasks";
    }

    private String userTaskId;
    private String studentId;
    private String taskId;
    private String content;
    private String attachement;
    private String parentId;
    private String classId;
    private String kinderId;

    public String getUserTaskId() {
        return userTaskId;
    }

    public void setUserTaskId(String userTaskId) {
        this.userTaskId = userTaskId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachement() {
        return attachement;
    }

    public void setAttachement(String attachement) {
        this.attachement = attachement;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getKinderId() {
        return kinderId;
    }

    public void setKinderId(String kinderId) {
        this.kinderId = kinderId;
    }
}
