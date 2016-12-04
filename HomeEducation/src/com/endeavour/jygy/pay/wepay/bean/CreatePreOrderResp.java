package com.endeavour.jygy.pay.wepay.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.endeavour.jygy.common.base.BaseResp;

/**
 * 微信预定单返回
 * Created by wu on 16/6/5.
 */
public class CreatePreOrderResp extends BaseResp {


    /**
     * return_code : SUCCESS
     * return_msg : OK
     * appid : wxa00827454764e4ee
     * mch_id : 1345028501
     * device_info : WEB
     * nonce_str : IFb4oazSm6ejHf3h
     * sign : 4C51F5100E5368C7D5722CA239F71BA7
     * result_code : SUCCESS
     * err_code : null
     * err_code_des : null
     * trade_type : APP
     * prepay_id : wx2016060512250343df85dae10569412202
     * code_url : null
     */

    private String appid;

    private String partnerid;

    private String prepayid;

    @JSONField(name = "package")
    private String package_;

    private String noncestr;

    private String timestamp;

    private String sign;


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackage_() {
        return package_;
    }

    public void setPackage_(String package_) {
        this.package_ = package_;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
