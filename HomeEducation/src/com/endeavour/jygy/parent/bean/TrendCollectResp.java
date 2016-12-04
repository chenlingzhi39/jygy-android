package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseResp;

public class TrendCollectResp extends BaseResp {
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFriendMsgId() {
		return friendMsgId;
	}
	public void setFriendMsgId(String friendMsgId) {
		this.friendMsgId = friendMsgId;
	}
	public String getfMsgCommentId() {
		return fMsgCommentId;
	}
	public void setfMsgCommentId(String fMsgCommentId) {
		this.fMsgCommentId = fMsgCommentId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	private String type;
    private String friendMsgId;
    private String fMsgCommentId;
    private String createTime;
    private String userName;
    private String content;
}
