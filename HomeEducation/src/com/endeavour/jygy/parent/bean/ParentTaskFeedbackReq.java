package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/17.
 */
public class ParentTaskFeedbackReq extends BaseReq {

    @Override
    public String getUrl() {
        return "parent/replyFeedBack";
    }

    private String userTaskId;
    private String studentId;
    private String taskId;
    private String content;
    private String parentId;
    private String classId;
    private String kinderId;
    private String replyId;
    private String replyWeek;

    public String getReplyWeek() {
        return replyWeek;
    }

    public void setReplyWeek(String replyWeek) {
        this.replyWeek = replyWeek;
    }

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

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }
}
