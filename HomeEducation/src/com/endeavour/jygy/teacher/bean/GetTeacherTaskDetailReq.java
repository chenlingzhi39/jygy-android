package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/15.
 */
public class GetTeacherTaskDetailReq extends BaseReq {

    @Override
    public String getUrl() {
        return "student/getTaskContent";
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
