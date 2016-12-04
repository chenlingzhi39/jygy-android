package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseResp;

/**
 * Created by wu on 15/12/3.
 */
public class GetFoodResp extends BaseResp {

    private String content;
    private String title;
    private String id;
    private String yearMonth;
    private String attachments;
    private String week;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
