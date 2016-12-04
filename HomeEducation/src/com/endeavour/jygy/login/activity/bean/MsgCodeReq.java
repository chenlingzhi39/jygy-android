package com.endeavour.jygy.login.activity.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * 获取验证码请求
 * @author wu
 *
 */
public class MsgCodeReq extends BaseReq{

	@Override
	public String getUrl() {
		return "index/getcaptcha";
	}

	private String mobileNo;
	
	public String getMobileNo() {
		return mobileNo;
	}
	
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

}
