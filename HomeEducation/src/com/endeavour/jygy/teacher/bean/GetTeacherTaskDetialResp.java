package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseResp;

/**
 * Created by wu on 15/12/15.
 */
public class GetTeacherTaskDetialResp extends BaseResp {

    private String content;
    private String id;
    private String completeAttachement;
    private String completeUserName;
    private String completeTime;
    private String completeContent;
    private String status;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompleteAttachement() {
        return completeAttachement;
    }

    public void setCompleteAttachement(String completeAttachement) {
        this.completeAttachement = completeAttachement;
    }

    public String getCompleteUserName() {
        return completeUserName;
    }

    public void setCompleteUserName(String completeUserName) {
        this.completeUserName = completeUserName;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getCompleteContent() {
        return completeContent;
    }

    public void setCompleteContent(String completeContent) {
        this.completeContent = completeContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
