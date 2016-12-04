package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * 获取宝宝信息
 * @author wu
 *
 */
public class GetBabyInfoReq extends BaseReq {

	@Override
	public String getUrl() {
		return "student/info";
	}

	private String userId;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
