package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/20.
 */
public class UpdateUserReq extends BaseReq {

    @Override
    public String getUrl() {
        return "user/update";
    }

    private Student studentReq;
    private Parent parents;

    public Parent getParents() {
        return parents;
    }

    public void setParents(Parent parents) {
        this.parents = parents;
    }

    public Student getStudentReq() {
        return studentReq;
    }

    public void setStudentReq(Student studentReq) {
        this.studentReq = studentReq;
    }
}
