package com.endeavour.jygy.parent.bean;

import java.io.Serializable;

/**
 * Created by wu on 15/12/10.
 */
public class DynamicDiscuss implements Serializable {

    private String id;
    private String createTime;
    private String parentId;
    private String reUserId;
    private String reUserName;
    private String lastTime;
    private String commentContent;
    private String type;
    private String beUserId;
    private String beUserName;
    private String reStudentId;
    private String beStudentId;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getReUserId() {
        return reUserId;
    }

    public void setReUserId(String reUserId) {
        this.reUserId = reUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getReUserName() {
        return reUserName;
    }

    public void setReUserName(String reUserName) {
        this.reUserName = reUserName;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBeUserId() {
        return beUserId;
    }

    public void setBeUserId(String beUserId) {
        this.beUserId = beUserId;
    }

    public String getBeUserName() {
        return beUserName;
    }

    public void setBeUserName(String beUserName) {
        this.beUserName = beUserName;
    }
}
