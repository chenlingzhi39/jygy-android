package net.hy.android.media;

import com.endeavour.jygy.common.base.BaseReq;

/**
 * Created by hanyin on 2016/7/15.
 */
public class GetChildAnimationsReq extends BaseReq {
    @Override
    public String getUrl() {
        return "animation/getChildAnimations";
    }

    private String gradeLevel;
    private String semester;
    private String lastTime;

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
