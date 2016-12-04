package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

public class TrendCollectReq extends BaseReq {
    @Override
    public String getUrl() {
        return "friendMsg/trendCollect";
    }
    private String parentId;
    private String updateTime;
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
