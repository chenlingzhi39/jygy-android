package com.endeavour.jygy.teacher.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/12/19.
 */
public class TeacherContentDetialBean implements Serializable {

    private String contetn;
    private String title;
    private int level;
    private List<TeacherContentDetialBean> list = new ArrayList<>();

    public List<TeacherContentDetialBean> getList() {
        return list;
    }

    public void setList(List<TeacherContentDetialBean> list) {
        this.list = list;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getContetn() {
        return contetn;
    }

    public void setContetn(String contetn) {
        this.contetn = contetn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
