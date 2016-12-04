package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseResp;

/**
 * Created by wu on 16/6/27.
 */
public class GetPayInfoResp extends BaseResp {

    private String id;
    private String feeName;
    private String feeAmount;
    private String expiryStartDate;
    private String expiryEndDate;
    private String descn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getExpiryStartDate() {
        return expiryStartDate;
    }

    public void setExpiryStartDate(String expiryStartDate) {
        this.expiryStartDate = expiryStartDate;
    }

    public String getExpiryEndDate() {
        return expiryEndDate;
    }

    public void setExpiryEndDate(String expiryEndDate) {
        this.expiryEndDate = expiryEndDate;
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn;
    }
}
