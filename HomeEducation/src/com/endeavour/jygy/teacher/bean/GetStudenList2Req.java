package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/14.
 */
public class GetStudenList2Req extends BaseReq {

    @Override
    public String getUrl() {
        return "student/getStudentsByClassId";
    }

    private String classId;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
