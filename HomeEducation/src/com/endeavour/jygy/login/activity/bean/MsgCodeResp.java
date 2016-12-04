package com.endeavour.jygy.login.activity.bean;

import com.endeavour.jygy.common.base.BaseResp;

/**
 * 获取验证码返回
 * @author wu
 *
 */
public class MsgCodeResp extends BaseResp{

	private String hint;//当warning为1,2时请将hint消息以提示框弹出.

	private int warning;//当warning为1,2时请将hint消息以提示框弹出.

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public int getWarning() {
		return warning;
	}

	public void setWarning(int warning) {
		this.warning = warning;
	}
}
