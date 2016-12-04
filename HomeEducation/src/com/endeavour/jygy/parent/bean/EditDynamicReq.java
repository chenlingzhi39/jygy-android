package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/12.
 */
public class EditDynamicReq extends BaseReq {

    @Override
    public String getUrl() {
        return "friendMsg/issue";
    }

    private String msgId;
    private String content;
    private String title;
    private String userId;
    private String classId;
    private String attachs;
    private String studentId;
    public String getKinderId() {
		return kinderId;
	}

	public void setKinderId(String kinderId) {
		this.kinderId = kinderId;
	}

	private String kinderId;
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getAttachs() {
        return attachs;
    }

    public void setAttachs(String attachs) {
        this.attachs = attachs;
    }
}
