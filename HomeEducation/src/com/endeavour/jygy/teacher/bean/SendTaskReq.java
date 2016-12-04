package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/27.
 */
public class SendTaskReq extends BaseReq {

    @Override
    public String getUrl() {
        return "index/subTasks";
    }

    private String taskId;
    private String userId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
