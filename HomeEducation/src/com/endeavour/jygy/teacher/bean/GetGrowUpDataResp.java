package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseResp;

import java.util.List;

/**
 * Created by wu on 15/12/14.
 */
public class GetGrowUpDataResp extends BaseResp {

    private String pageCount;
    private String pageSize;
    private String totalRecords;
    private String pageIndex;
    private List<GrowUpBean> data;

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public List<GrowUpBean> getData() {
        return data;
    }

    public void setData(List<GrowUpBean> data) {
        this.data = data;
    }
}
