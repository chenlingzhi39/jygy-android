package com.endeavour.jygy.login.activity.bean;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by wu on 15/11/24.
 */
public class GetSchoolByLocationReq extends BaseReq {

    @Override
    public String getUrl() {
        return "index/getKinder";
    }

    private String province;
    private String city;
    private String schoolId;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
}
