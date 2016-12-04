package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/14.
 */
public class GetGrowUpDataReq extends BaseReq {

    @Override
    public String getUrl() {
        return "student/getUserWorksData";
    }

    private String type;
    private String userid;
    private String kindergartenId;
    private String kindergartenClassId;
    private String pageNum;
    private String pageSize;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getKindergartenId() {
        return kindergartenId;
    }

    public void setKindergartenId(String kindergartenId) {
        this.kindergartenId = kindergartenId;
    }

    public String getKindergartenClassId() {
        return kindergartenClassId;
    }

    public void setKindergartenClassId(String kindergartenClassId) {
        this.kindergartenClassId = kindergartenClassId;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
