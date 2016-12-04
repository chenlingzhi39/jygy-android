package com.endeavour.jygy.parent.activity;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/5.
 */
public class GetDynamicDiscussReq extends BaseReq {

    @Override
    public String getUrl() {
        return "parent/getMsgComment";
    }

    private String msgId;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
