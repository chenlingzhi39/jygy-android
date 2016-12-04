package com.endeavour.jygy.common.view;

import java.io.Serializable;

/**
 * Created by wu on 15/12/23.
 */
public class AttathBeans implements Serializable {

    private int type;
    private String imgUrl;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
