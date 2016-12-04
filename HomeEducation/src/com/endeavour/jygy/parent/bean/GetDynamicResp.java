package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseResp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/12/2.
 */
public class GetDynamicResp extends BaseResp {
    private int counts;
    private List<Dynamic> logs = new ArrayList<>();

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public List<Dynamic> getLogs() {
        return logs;
    }

    public void setLogs(List<Dynamic> logs) {
        this.logs = logs;
    }
}
