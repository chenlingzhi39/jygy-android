package com.endeavour.jygy.parent.bean;

import java.io.Serializable;

/**
 * Created by wu on 15/12/19.
 */
public class GetParentLessonPracticeDet implements Serializable {

    private String userPracId;
    private String readStatus;
    private String practiceId;
    private String practiceContent;
    private String practiceTitle;
    private String practiceAttachs;

    public String getUserPracId() {
        return userPracId;
    }

    public void setUserPracId(String userPracId) {
        this.userPracId = userPracId;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getPracticeId() {
        return practiceId;
    }

    public void setPracticeId(String practiceId) {
        this.practiceId = practiceId;
    }

    public String getPracticeContent() {
        return practiceContent;
    }

    public void setPracticeContent(String practiceContent) {
        this.practiceContent = practiceContent;
    }

    public String getPracticeTitle() {
        return practiceTitle;
    }

    public void setPracticeTitle(String practiceTitle) {
        this.practiceTitle = practiceTitle;
    }

    public String getPracticeAttachs() {
        return practiceAttachs;
    }

    public void setPracticeAttachs(String practiceAttachs) {
        this.practiceAttachs = practiceAttachs;
    }
}
