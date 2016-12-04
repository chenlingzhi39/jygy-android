package com.endeavour.jygy.parent.bean;

import java.io.Serializable;

/**
 * 任务反馈
 * 
 * @author wu
 * 
 */
public class TaskFeedBack implements Serializable {

	private long tasksId;
	private String content;
	private String createTime;// 创建时间(yyyy-MM-dd HH:mm:ss)
	private String endDate;// 反馈截止日期(yyyy-MM-dd)
	private String lastTime;// 更新时间(yyyy-MM-dd HH:mm:ss)
	private String status;// 状态
	private long userId;// 用户ID(学生ID)

	public long getTasksId() {
		return tasksId;
	}

	public void setTasksId(long tasksId) {
		this.tasksId = tasksId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getUserId() {
		return userId;
	}

}
