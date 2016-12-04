package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseResp;

/**
 * Created by wu on 16/1/27.
 */
public class GetStudentMoralResp extends BaseResp {


    /**
     * id : 1
     * userId : 238
     * moral : 5
     * lastTime : 1453789688000
     * kindergartenClassId : 95
     * kindergartenId : 82
     * gradeLevel : 2
     * semester : 1
     */

    private int id;
    private int userId;
    private int moral;
    private long lastTime;
    private int kindergartenClassId;
    private int kindergartenId;
    private int gradeLevel;
    private int semester;

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMoral(int moral) {
        this.moral = moral;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public void setKindergartenClassId(int kindergartenClassId) {
        this.kindergartenClassId = kindergartenClassId;
    }

    public void setKindergartenId(int kindergartenId) {
        this.kindergartenId = kindergartenId;
    }

    public void setGradeLevel(int gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getMoral() {
        return moral;
    }

    public long getLastTime() {
        return lastTime;
    }

    public int getKindergartenClassId() {
        return kindergartenClassId;
    }

    public int getKindergartenId() {
        return kindergartenId;
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    public int getSemester() {
        return semester;
    }
}
