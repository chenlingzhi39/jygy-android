package com.endeavour.jygy.parent.bean;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wu on 15/12/2.
 */
@Table(name = "Dynamic8")
public class Dynamic implements Serializable {

    private String content;
    private String id;
    private String createTime;
    private String title;
    private String classId;
    private List<String> attachs;
    private String userId;
    private String likes;
    private String userName;
    private String comments;
    private String headPortrait;
    private String studentId;
    private List<DynamicDiscuss> msgComments;
    private List<DynamicDiscuss> msgLikes;
    private String json;
    private String recommendContent;

    private String className;

    private int showDiscussPopup;
    private int attachType;

    private boolean isTask;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public List<DynamicDiscuss> getMsgComments() {
        return msgComments;
    }

    public void setMsgComments(List<DynamicDiscuss> msgComments) {
        this.msgComments = msgComments;
    }

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<String> getAttachs() {
        return attachs;
    }

    public void setAttachs(List<String> attachs) {
        this.attachs = attachs;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }


    public List<DynamicDiscuss> getMsgLikes() {
        return msgLikes;
    }

    public void setMsgLikes(List<DynamicDiscuss> msgLikes) {
        this.msgLikes = msgLikes;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public int getShowDiscussPopup() {
        return showDiscussPopup;
    }

    public void setShowDiscussPopup(int showDiscussPopup) {
        this.showDiscussPopup = showDiscussPopup;
    }

    public int getAttachType() {
        return attachType;
    }

    public void setAttachType(int attachType) {
        this.attachType = attachType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRecommendContent() {
        return recommendContent;
    }

    public void setRecommendContent(String recommendContent) {
        this.recommendContent = recommendContent;
    }

    public boolean isTask() {
        return isTask;
    }

    public void setTask(boolean task) {
        isTask = task;
    }

    public void adapte() {
        if (!TextUtils.isEmpty(recommendContent)) {
            RecommendContent recommendContent = toRecommendContent();
            setUserName(recommendContent.getStuName());
            setClassName(recommendContent.getClassName());
            setContent("    " + recommendContent.getLessonPlanTitle() + "\n" + "\n" + "   " + recommendContent.getCompleteContent());
            setAttachs(recommendContent.getCompleteAttachement());
            setTask(true);
        }
    }

    @Nullable
    public RecommendContent toRecommendContent() {
        return JSONObject.parseObject(this.recommendContent, RecommendContent.class);
    }
}
