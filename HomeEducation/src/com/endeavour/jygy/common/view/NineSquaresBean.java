package com.endeavour.jygy.common.view;

import java.io.Serializable;

/**
 * Created by wu on 15/12/30.
 */
public class NineSquaresBean implements Serializable {

    private int type;
    private String path;
    private String attachPath;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAttachPath() {
        return attachPath;
    }

    public void setAttachPath(String attachPath) {
        this.attachPath = attachPath;
    }
}
