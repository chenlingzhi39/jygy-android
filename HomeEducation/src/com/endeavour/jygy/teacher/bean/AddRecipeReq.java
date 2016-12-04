package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 16/7/24.
 */
public class AddRecipeReq extends BaseReq {

    @Override
    public String getUrl() {
        return "recipe/addRecipe";
    }

    private String leaderId;
    private String yearMonth;
    private String week;
    private String content;
    private String attachs;

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachs() {
        return attachs;
    }

    public void setAttachs(String attachs) {
        this.attachs = attachs;
    }
}
