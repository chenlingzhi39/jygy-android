package com.endeavour.jygy.login.activity.bean;

import com.endeavour.jygy.common.base.BaseReq;

public class LoginReq extends BaseReq {

    @Override
    public String getUrl() {
        return "index/login";
    }

    private String mobileNo;
    private String passwd;
    private Device device;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPasswd() {
        return passwd;
    }
}
