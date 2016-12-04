package com.endeavour.jygy.login.activity.bean;

import com.endeavour.jygy.common.base.BaseReq;


/**
 * 修改密码
 * @author wu
 *
 */
public class UpdatePasswdReq extends BaseReq {

	private String userId;
	private String oldPasswd;
	private String newPasswd;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getOldPasswd() {
		return oldPasswd;
	}
	
	public void setOldPasswd(String oldPasswd) {
		this.oldPasswd = oldPasswd;
	}
	
	public void setNewPasswd(String newPasswd) {
		this.newPasswd = newPasswd;
	}
	
	public String getNewPasswd() {
		return newPasswd;
	}
	
}
