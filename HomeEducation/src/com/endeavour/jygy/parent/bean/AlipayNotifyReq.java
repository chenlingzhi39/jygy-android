package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 16/6/29.
 */
public class AlipayNotifyReq extends BaseReq {

    @Override
    public String getUrl() {
        return "common/alipayNotify";
    }

    private PayBody body;

    public PayBody getBody() {
        return body;
    }

    public void setBody(PayBody body) {
        this.body = body;
    }
}
