package com.endeavour.jygy.parent.bean;

import java.io.Serializable;

/**
 * Created by wu on 15/11/27.
 */
public class Parent implements Serializable {
    private String kinderId;
    private String parentName;
    private String mobileNo;
    private String protectRole;
    private String headPortrait;

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getKinderId() {
        return kinderId;
    }

    public void setKinderId(String kinderId) {
        this.kinderId = kinderId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getProtectRole() {
        return protectRole;
    }

    public void setProtectRole(String protectRole) {
        this.protectRole = protectRole;
    }
}
