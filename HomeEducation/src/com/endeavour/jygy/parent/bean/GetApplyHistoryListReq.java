package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 16/7/6.
 */
public class GetApplyHistoryListReq extends BaseReq {

    @Override
    public String getUrl() {
        return "student/getApplyHistoryList";
    }

    private String studentId;
    private String lastTime;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
