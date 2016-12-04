package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseResp;

/**
 * Created by wu on 16/6/29.
 */
public class GetMyPayRecordsResp extends BaseResp {

    private String id;
    private String tranLogId;
    private String tranType;
    private String tranTime;
    private String tranDescn;
    private String tranAmount;

    private GetPayInfoResp feeChannels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTranLogId() {
        return tranLogId;
    }

    public void setTranLogId(String tranLogId) {
        this.tranLogId = tranLogId;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getTranTime() {
        return tranTime;
    }

    public void setTranTime(String tranTime) {
        this.tranTime = tranTime;
    }

    public String getTranDescn() {
        return tranDescn;
    }

    public void setTranDescn(String tranDescn) {
        this.tranDescn = tranDescn;
    }

    public String getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(String tranAmount) {
        this.tranAmount = tranAmount;
    }

    public GetPayInfoResp getFeeChannels() {
        return feeChannels;
    }

    public void setFeeChannels(GetPayInfoResp feeChannels) {
        this.feeChannels = feeChannels;
    }
}
