package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

import java.util.List;

/**
 * Created by wu on 15/11/27.
 */
public class AddBabyReq extends BaseReq {

    @Override
    public String getUrl() {
        return "user/add";
    }

    private String userId;
    private String classId;
    private Student studentReq;
    private String kinderId;
    private List<Parent> parents;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public List<Parent> getParents() {
        return parents;
    }

    public void setParents(List<Parent> parents) {
        this.parents = parents;
    }

    public Student getStudentReq() {
        return studentReq;
    }

    public void setStudentReq(Student studentReq) {
        this.studentReq = studentReq;
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
}
