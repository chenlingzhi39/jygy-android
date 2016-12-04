package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/13.
 */
public class SendDynamicDiscussReq extends BaseReq {

    @Override
    public String getUrl() {
        return "friendMsg/replyComment";
    }

    private String msgId;
    private String commentId;
    private String content;
    private String type;
    private String userId;
    private String beUserId;
    private String reStudentId;
    private String beStudentId;

    public String getReStudentId() {
        return reStudentId;
    }

    public void setReStudentId(String reStudentId) {
        this.reStudentId = reStudentId;
    }

    public String getBeStudentId() {
        return beStudentId;
    }

    public void setBeStudentId(String beStudentId) {
        this.beStudentId = beStudentId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBeUserId() {
        return beUserId;
    }

    public void setBeUserId(String beUserId) {
        this.beUserId = beUserId;
    }
}
