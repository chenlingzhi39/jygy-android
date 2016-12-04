package com.endeavour.jygy.parent.bean;

import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

@Table(name = "message6")
public class Message implements Serializable {
	private String content;
	private String contentHtml;
	public String getContentHtml() {
		return contentHtml;
	}

	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}

	private String type;
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String messid;
	private String userName;
	private String attendDays;
	public String getAttendDays() {
		return attendDays;
	}

	public void setAttendDays(String attendDays) {
		this.attendDays = attendDays;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMessid() {
		return messid;
	}

	public void setMessid(String messid) {
		this.messid = messid;
	}

	private String messtime; //如请假中的起止时间

	public String getMesstime() {
		return messtime;
	}

	public void setMesstime(String messtime) {
		this.messtime = messtime;
	}

	public int getIsread() {
		return isread;
	}

	public void setIsread(int isread) {
		this.isread = isread;
	}

	private int isread; // 0未阅读 1已阅读

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	private String title;
	private String createTime; //消息创建时间
	private long updateTime; //增量时间戳
	public String getContent() {
		return content;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
