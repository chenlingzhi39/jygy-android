package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseResp;

/**
 * Created by wu on 15/12/19.
 */
public class TeacherPlanDetailTask extends BaseResp {

    private String content;
    private int taskId;
    private String title;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
