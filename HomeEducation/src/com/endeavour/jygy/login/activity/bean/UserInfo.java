package com.endeavour.jygy.login.activity.bean;

import java.io.Serializable;

/**
 * Created by wu on 15/11/26.
 */
public class UserInfo implements Serializable {

    public static final String ROLE_LEADER_ADMIN = "ROLE_LEADER_ADMIN"; //园长
    public static final String ROLE_RESTRICTED_ADMIN = "ROLE_RESTRICTED_ADMIN"; //平台运维
    public static final String ROLE_USER = "ROLE_USER"; //家长
    public static final String ROLE_TEACHER = "ROLE_TEACHER"; //老师
    public static final String ROLE_ADMIN = "ROLE_ADMIN"; //超管

    private String roleKey;
    private String roleValue;
    private String userId;
    private String kinderId;
    private String passwd;
    private String userName;
    private String classId;
    private String gradeLevel;
    private String semester;
    private String headPortrait;

    private String gradeNick;
    private String classNick;
    private String phoneNum;

    public String getClassNick() {
        return classNick;
    }

    public void setClassNick(String classNick) {
        this.classNick = classNick;
    }

    public String getGradeNick() {
        return gradeNick;
    }

    public void setGradeNick(String gradeNick) {
        this.gradeNick = gradeNick;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public String getRoleValue() {
        return roleValue;
    }

    public void setRoleValue(String roleValue) {
        this.roleValue = roleValue;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKinderId() {
        return kinderId;
    }

    public void setKinderId(String kinderId) {
        this.kinderId = kinderId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
