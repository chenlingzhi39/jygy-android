package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseResp;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by wu on 15/12/14.
 */
@Table(name = "GetLessonPlansResp2")
public class GetLessonPlansResp extends BaseResp {

    private String id;
    private String title;
    private String validFlag;
    private String updateTime;
    private String createTime;
    private String serialNumber;
    private String gradeLevel;
    private String semester;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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
