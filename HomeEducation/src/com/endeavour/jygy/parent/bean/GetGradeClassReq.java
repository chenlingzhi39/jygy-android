package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/11/26.
 */
public class GetGradeClassReq extends BaseReq {

    @Override
    public String getUrl() {
        return "index/getGradeClass";
    }

    private String kindergartenId;

    public String getKindergartenId() {
        return kindergartenId;
    }

    public void setKindergartenId(String kindergartenId) {
        this.kindergartenId = kindergartenId;
    }
}
