package com.endeavour.jygy.login.activity.bean;

import com.endeavour.jygy.common.base.BaseResp;
import com.endeavour.jygy.parent.bean.Student;

import java.util.List;

public class LoginResp extends BaseResp {
    private UserInfo userInfo;
    private List<Student> childInfo;
    private String graduationFlag;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<Student> getChildInfo() {
        return childInfo;
    }

    public void setChildInfo(List<Student> childInfo) {
        this.childInfo = childInfo;
    }

    public String getGraduationFlag() {
        return graduationFlag;
    }

    public void setGraduationFlag(String graduationFlag) {
        this.graduationFlag = graduationFlag;
    }
}
