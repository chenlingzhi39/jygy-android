package com.endeavour.jygy.pay.wepay.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * 微信预定单接口
 * Created by wu on 16/6/5.
 */
public class CreatePreOrderReq extends BaseReq {

    @Override
    public String getUrl() {
        return "common/wxPrePay";
    }

    private String body; // 商品或支付单简要描述 ,
    private String attach;
    private String detail; // 商品名称明细列表 ,
    private String spbillCreateIp; //(string): 用户端实际ip ,
    private int totalFee;// (string): 订单总金额，单位为分

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }
}
