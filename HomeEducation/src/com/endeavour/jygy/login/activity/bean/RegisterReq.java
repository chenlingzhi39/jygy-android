package com.endeavour.jygy.login.activity.bean;

import com.endeavour.jygy.common.base.BaseReq;

public class RegisterReq extends BaseReq {

    @Override
    public String getUrl() {
        return "index/register";
    }

    private String mobileNo;
    private String userName;//家长姓名
    private String passwd;
    private String captcha;//验证码
    private String province;//省
    private String city;
//    private String parentsName;
    private String protectRole;//1 父亲 2母亲 3 长辈 4 保姆 5其他
    private int kindergartenId; //幼儿园ID
    private Device device;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

//    public String getParentsName() {
//        return parentsName;
//    }
//
//    public void setParentsName(String parentsName) {
//        this.parentsName = parentsName;
//    }

    public String getProtectRole() {
        return protectRole;
    }

    public void setProtectRole(String protectRole) {
        this.protectRole = protectRole;
    }

    public int getKindergartenId() {
        return kindergartenId;
    }

    public void setKindergartenId(int kindergartenId) {
        this.kindergartenId = kindergartenId;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
