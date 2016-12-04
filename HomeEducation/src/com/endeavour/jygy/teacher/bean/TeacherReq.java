package com.endeavour.jygy.teacher.bean;


import com.endeavour.jygy.common.base.BaseReq;

public class TeacherReq extends BaseReq {

	@Override
	public String getUrl() {
		return "user/teacherUpdate";
	}

	private String teacherId; //用户ID(教师ID)
	private String name;
	private String headUrl;// 头像(新头像地址)

	public TeacherReq() {
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

}
