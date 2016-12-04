package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseResp;

public class GetNoticeAnnounceResp extends BaseResp {
    private String lastTime;
    private String content;
    private String title;
    private String noticeId;

    public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getContent() {
        return content;
    }

    public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
