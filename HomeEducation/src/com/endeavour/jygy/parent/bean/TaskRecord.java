package com.endeavour.jygy.parent.bean;

import java.io.Serializable;

public class TaskRecord implements Serializable {

	private long taskId;//任务编号
	private int amount;//分值(德育分)
	private long userId;//用户ID(学生ID)
	
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	
	public long getTaskId() {
		return taskId;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
}
