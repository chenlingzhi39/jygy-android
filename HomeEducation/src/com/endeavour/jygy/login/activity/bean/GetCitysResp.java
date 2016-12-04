package com.endeavour.jygy.login.activity.bean;

import com.endeavour.jygy.common.base.BaseResp;

/**
 * Created by wu on 16/1/5.
 */
public class GetCitysResp extends BaseResp {


    /**
     * id : 110101
     * name : 东城区
     * parentId : 110100
     * shortName : 东城
     * levelType : 3
     * cityCode : 010
     * zipCode : 100010
     * lng : 116.41005
     * lat : 39.93157
     * pinyin : Dongcheng
     * status : 1
     */

    private int id;
    private String name;
    private String parentId;
    private String shortName;
    private String levelType;
    private String cityCode;
    private String zipCode;
    private String lng;
    private String lat;
    private String pinyin;
    private String status;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setLevelType(String levelType) {
        this.levelType = levelType;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getParentId() {
        return parentId;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLevelType() {
        return levelType;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getLng() {
        return lng;
    }

    public String getLat() {
        return lat;
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getStatus() {
        return status;
    }
}
