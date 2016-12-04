package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseResp;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by wu on 15/12/16.
 */
@Table(name = "GetTeacherTaskFeedbackListResp6")
public class GetTeacherTaskFeedbackListResp extends BaseResp {

    private String id;
    private String lastTime;
    private String createTime;
    private String studentId;
    private String userName;
    private String headPortrait;
    private String taskTitle;
    private String replyWeek;
    private String classId;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getReplyWeek() {
        return replyWeek;
    }

    public void setReplyWeek(String replyWeek) {
        this.replyWeek = replyWeek;
    }

    public static final String READED = "1";

    private String isReaded;

    public void setReaded() {
        this.isReaded = READED;
    }

    public boolean isReaded() {
        return (READED.equals(isReaded));
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public static String getREADED() {
        return READED;
    }

    public String getIsReaded() {
        return isReaded;
    }

    public void setIsReaded(String isReaded) {
        this.isReaded = isReaded;
    }

}
