package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/3.
 */
public class GetFoodReq extends BaseReq {

    @Override
    public String getUrl() {
        return "parent/getRecipe";
    }

    private String kinderId;

    public String getKinderId() {
        return kinderId;
    }

    public void setKinderId(String kinderId) {
        this.kinderId = kinderId;
    }
}
