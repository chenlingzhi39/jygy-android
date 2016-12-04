package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseResp;

/**
 * Created by wu on 15/12/13.
 */
public class GetNoticeResp extends BaseResp {

    private String create_time;
    private String content;
    private String title;
    private String type;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getContent() {
        return content;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
