package com.endeavour.jygy.parent.bean;

import java.io.Serializable;

/**
 * Created by wu on 15/11/27.
 */
public class Student implements Serializable {
    private String gradeId;
    private String studentId;
    private String classId;
    private String className;
    private String name;
    private String sex;
    private String birthday;
    private String headPortrait;
    private String userId;
    private String classNick;
    private String gradeNick;
    private String validFlag; //0 申请中 1: 通过
    private String gradeLevel;
    private String semester;
    private String moral;
    private String otherId;
    private String kindergartenName;
    private String area;
    private String graduationFlag;

    public String getGraduationFlag() {
        return graduationFlag;
    }

    public void setGraduationFlag(String graduationFlag) {
        this.graduationFlag = graduationFlag;
    }

    public static String getValidPass() {
        return VALID_PASS;
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassNick() {
        return classNick;
    }

    public void setClassNick(String classNick) {
        this.classNick = classNick;
    }

    public static final String VALID_PASS = "0";

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

    public String getMoral() {
        return moral;
    }

    public void setMoral(String moral) {
        this.moral = moral;
    }


    public String getGradeNick() {
        return gradeNick;
    }

    public void setGradeNick(String gradeNick) {
        this.gradeNick = gradeNick;
    }

    public String getKindergartenName() {
        return kindergartenName;
    }

    public void setKindergartenName(String kindergartenName) {
        this.kindergartenName = kindergartenName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
