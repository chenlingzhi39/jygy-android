package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseResp;

/**
 * Created by wu on 15/12/14.
 */
public class GetStudenList2Resp extends BaseResp {

    private String id;
    private String birthday;
    private String userName;
    private String sex;
    private String otherId;
    private String headPortrait;
    private String moral; //德育分
    private String gradeLevel;
    private String semester;;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getMoral() {
        return moral;
    }

    public void setMoral(String moral) {
        this.moral = moral;
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
}
