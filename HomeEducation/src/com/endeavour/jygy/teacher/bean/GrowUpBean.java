package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/12/14.
 */
public class GrowUpBean extends BaseReq {

    private String address;
    private String tempName;
    private String template_code;
    private String e_type;
    private String user_code;
    private String e_code;
    private String e_icon;
    private String e_name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public String getTemplate_code() {
        return template_code;
    }

    public void setTemplate_code(String template_code) {
        this.template_code = template_code;
    }

    public String getE_type() {
        return e_type;
    }

    public void setE_type(String e_type) {
        this.e_type = e_type;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getE_code() {
        return e_code;
    }

    public void setE_code(String e_code) {
        this.e_code = e_code;
    }

    public String getE_icon() {
        return e_icon;
    }

    public void setE_icon(String e_icon) {
        this.e_icon = e_icon;
    }

    public String getE_name() {
        return e_name;
    }

    public void setE_name(String e_name) {
        this.e_name = e_name;
    }
}
