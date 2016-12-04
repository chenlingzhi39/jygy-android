package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseResp;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by wu on 15/12/15.
 */
@Table(name = "GetTeacherTaskResp6")
public class GetTeacherTaskResp extends BaseResp {

    private String id;
    private String createTime;
    private String title;
    private String updateTime;
    private String headPortrait;
    private String userName;
    private String status;
    private String classId;
    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public static final String READED = "1";

    private String isReaded;

    public void setReaded() {
        this.isReaded = READED;
    }

    public boolean isReaded() {
        return (READED.equals(isReaded));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIsReaded() {
        return isReaded;
    }

    public void setIsReaded(String isReaded) {
        this.isReaded = isReaded;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
