package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseResp;

/**
 * Created by wu on 15/12/14.
 */
public class TaskReply extends BaseResp {
    private String id;
    private String content;//反馈内容
    private String status;//反馈状态,"0 未反馈 1反馈"
    private String endDate;//截止反馈日期
    private String attachments; //附件
    private String expiredFlag; //是否以过期 0:未过期 1已过期

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public String getExpiredFlag() {
        return expiredFlag;
    }

    public void setExpiredFlag(String expiredFlag) {
        this.expiredFlag = expiredFlag;
    }
}
