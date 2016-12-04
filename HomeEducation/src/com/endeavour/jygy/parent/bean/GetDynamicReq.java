package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/2.
 */
public class GetDynamicReq extends BaseReq{

    public static final String refresh = "refresh";
    public static final String loadMore = "loadMore";

    @Override
    public String getUrl() {
        return "parent/getClsMessage";
    }

    private String classId;
    private String perNo;
    private String lastTime;
    private String refreshType ; //refresh loadMore
    private String kinderId ; //refresh loadMore

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getPerNo() {
        return perNo;
    }

    public void setPerNo(String perNo) {
        this.perNo = perNo;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getRefreshType() {
        return refreshType;
    }

    public void setRefreshType(String refreshType) {
        this.refreshType = refreshType;
    }

    public String getKinderId() {
        return kinderId;
    }

    public void setKinderId(String kinderId) {
        this.kinderId = kinderId;
    }
}
