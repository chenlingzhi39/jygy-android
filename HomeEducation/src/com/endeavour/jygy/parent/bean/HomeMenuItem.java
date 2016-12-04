package com.endeavour.jygy.parent.bean;

/**
 * Created by wu on 15/11/26.
 */
public class HomeMenuItem {

    public HomeMenuItem(String name, int resID) {
        this.name = name;
        this.resID = resID;
    }

    private String name;
    private int resID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }
}
