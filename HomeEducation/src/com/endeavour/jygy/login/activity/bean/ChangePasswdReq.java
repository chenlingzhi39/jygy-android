package com.endeavour.jygy.login.activity.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/23.
 */
public class ChangePasswdReq extends BaseReq {

    @Override
    public String getUrl() {
        return "index/updatePwd";
    }

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

    public String getNewPasswd() {
        return newPasswd;
    }

    public void setNewPasswd(String newPasswd) {
        this.newPasswd = newPasswd;
    }
}
