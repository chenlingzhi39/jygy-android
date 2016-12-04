package com.endeavour.jygy.login.activity.bean;

import com.endeavour.jygy.common.base.BaseResp;

/**
 * Created by wu on 15/11/25.
 */
public class GetSchoolByLocationResp extends BaseResp{

    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
