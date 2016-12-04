package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/3.
 */
public class GetFoodHistoryReq extends BaseReq {

    @Override
    public String getUrl() {
        return "recipe/historyRecipe";
    }

    private int pageNo;
    private int perNo;
    private String kinderId;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPerNo() {
        return perNo;
    }

    public void setPerNo(int perNo) {
        this.perNo = perNo;
    }

    public String getKinderId() {
        return kinderId;
    }

    public void setKinderId(String kinderId) {
        this.kinderId = kinderId;
    }
}
