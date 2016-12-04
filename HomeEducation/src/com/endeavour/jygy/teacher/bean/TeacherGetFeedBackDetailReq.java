package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/17.
 */
public class TeacherGetFeedBackDetailReq extends BaseReq {

    @Override
    public String getUrl() {
        return "student/findTaskRelayContent";
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
