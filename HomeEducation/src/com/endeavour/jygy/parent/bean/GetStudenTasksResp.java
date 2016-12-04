package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseResp;

import java.util.List;

/**
 * Created by wu on 15/12/14.
 */
public class GetStudenTasksResp extends BaseResp {

    private String taskId;
    private String taskContent;//任务内容
    private String taskTitle;//任务标题
    private String userTaskStatus;//用户任务状态
    private String userTasksId;//用户任务ＩＤ
    private String completeContent;//任务完成内容
    private String completeTime;//任务完成时间
    private String kinderId;//幼儿园ＩＤ
    private String classId;//班级ＩＤ
    private String reviewContent;//教师评论内容
    private String reviewTeacherName;//评论教师姓名
    private String reviewTime;//教师评论时间
    private String score; //得分
    private String star; //得星数
    private String completeAttach; //得星数
    private List<TaskReply> tasksReply; //任务反馈列表
    private String reviewTeacherId;
    private String lessonPlanId;
    private String childAnimationUrl;
    private String childAnimationDescn;
    private String childAnimationTitle;


    public String getChildAnimationUrl() {
        return childAnimationUrl;
    }

    public void setChildAnimationUrl(String childAnimationUrl) {
        this.childAnimationUrl = childAnimationUrl;
    }

    public String getChildAnimationDescn() {
        return childAnimationDescn;
    }

    public void setChildAnimationDescn(String childAnimationDescn) {
        this.childAnimationDescn = childAnimationDescn;
    }

    public String getChildAnimationTitle() {
        return childAnimationTitle;
    }

    public void setChildAnimationTitle(String childAnimationTitle) {
        this.childAnimationTitle = childAnimationTitle;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getUserTaskStatus() {
        return userTaskStatus;
    }

    public void setUserTaskStatus(String userTaskStatus) {
        this.userTaskStatus = userTaskStatus;
    }

    public String getUserTasksId() {
        return userTasksId;
    }

    public void setUserTasksId(String userTasksId) {
        this.userTasksId = userTasksId;
    }

    public String getCompleteContent() {
        return completeContent;
    }

    public void setCompleteContent(String completeContent) {
        this.completeContent = completeContent;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getKinderId() {
        return kinderId;
    }

    public void setKinderId(String kinderId) {
        this.kinderId = kinderId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getReviewTeacherName() {
        return reviewTeacherName;
    }

    public void setReviewTeacherName(String reviewTeacherName) {
        this.reviewTeacherName = reviewTeacherName;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
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

    public String getCompleteAttach() {
        return completeAttach;
    }

    public void setCompleteAttach(String completeAttach) {
        this.completeAttach = completeAttach;
    }

    public List<TaskReply> getTasksReply() {
        return tasksReply;
    }

    public void setTasksReply(List<TaskReply> tasksReply) {
        this.tasksReply = tasksReply;
    }

    public String getReviewTeacherId() {
        return reviewTeacherId;
    }

    public void setReviewTeacherId(String reviewTeacherId) {
        this.reviewTeacherId = reviewTeacherId;
    }

    public String getLessonPlanId() {
        return lessonPlanId;
    }

    public void setLessonPlanId(String lessonPlanId) {
        this.lessonPlanId = lessonPlanId;
    }
}
