package com.endeavour.jygy.login.activity.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 16/1/5.
 */
public class GetCitysReq extends BaseReq {
    @Override
    public String getUrl() {
        return "city/getCity";
    }

    private String parentId;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
