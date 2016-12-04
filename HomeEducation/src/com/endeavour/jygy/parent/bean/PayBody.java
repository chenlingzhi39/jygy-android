package com.endeavour.jygy.parent.bean;

import java.io.Serializable;

/**
 * Created by wu on 16/6/29.
 */
public class PayBody implements Serializable{

    private String userId;
    private String feeId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }
}
