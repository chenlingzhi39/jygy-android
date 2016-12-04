package com.endeavour.jygy.login.activity.bean;

import com.endeavour.jygy.common.base.BaseReq;

public class ResetPasswdReq extends BaseReq {

    @Override
    public String getUrl() {
        return "index/resetPwd";
    }

    private String mobileNo;
    private String newPasswd;//家长姓名
    private String captcha;//验证码

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getNewPasswd() {
        return newPasswd;
    }

    public void setNewPasswd(String newPasswd) {
        this.newPasswd = newPasswd;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
