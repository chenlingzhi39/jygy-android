package com.endeavour.jygy.parent.bean;

import java.io.Serializable;
import java.util.List;

public class Task implements Serializable {

	private long kindergartenCalssId;// 班级编号
	private String title;// 标题
	private String content;// 任务内容
	private long userId;// 用户ID(教师ID)
	private long createTime;// 创建时间(yyyy-MM-dd HH:mm:ss)
	private long completeContent;// 完成内容
	private List<TaskFeedBack> taskFeedBacks;
	private TaskRecord taskRecord;

	public long getKindergartenCalssId() {
		return kindergartenCalssId;
	}

	public void setKindergartenCalssId(long kindergartenCalssId) {
		this.kindergartenCalssId = kindergartenCalssId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getCompleteContent() {
		return completeContent;
	}

	public void setCompleteContent(long completeContent) {
		this.completeContent = completeContent;
	}

	public List<TaskFeedBack> getTaskFeedBacks() {
		return taskFeedBacks;
	}

	public void setTaskFeedBacks(List<TaskFeedBack> taskFeedBacks) {
		this.taskFeedBacks = taskFeedBacks;
	}

	public TaskRecord getTaskRecord() {
		return taskRecord;
	}

	public void setTaskRecord(TaskRecord taskRecord) {
		this.taskRecord = taskRecord;
	}

}
