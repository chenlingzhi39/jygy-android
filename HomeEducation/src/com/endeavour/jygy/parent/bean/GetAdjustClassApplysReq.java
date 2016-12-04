package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 16/7/11.
 */
public class GetAdjustClassApplysReq extends BaseReq {

    @Override
    public String getUrl() {
        return "student/getAdjustClassApplys";
    }

    private String teacherId;
    private String lastTime;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
