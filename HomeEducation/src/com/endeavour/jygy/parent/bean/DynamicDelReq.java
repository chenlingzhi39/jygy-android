package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/20.
 */
public class DynamicDelReq extends BaseReq {

    @Override
    public String getUrl() {
        return "friendMsg/delIssue";
    }

    private String msgId;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
