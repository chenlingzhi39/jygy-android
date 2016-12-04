package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/20.
 */
public class DynamicDiscussDelReq extends BaseReq {

    @Override
    public String getUrl() {
        return "friendMsg/delreply";
    }

    private String commentId;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}
