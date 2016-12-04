package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/16.
 */
public class TeacherFeedbackReq extends BaseReq {

    @Override
    public String getUrl() {
        return "student/taskGrading";
    }

    private String id;
    private String score;
    private String star;
    private String reviewContent;
    private String replyId;
    private String userId;
    private boolean isRecommend;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public boolean isRecommend() {
        return isRecommend;
    }

    public void setRecommend(boolean recommend) {
        isRecommend = recommend;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
