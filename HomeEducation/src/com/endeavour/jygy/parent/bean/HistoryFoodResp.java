package com.endeavour.jygy.parent.bean;

import com.endeavour.jygy.common.base.BaseResp;

import java.util.List;

/**
 * Created by wu on 15/12/12.
 */
public class HistoryFoodResp extends BaseResp {

    private List<GetFoodResp> logs;

    public List<GetFoodResp> getLogs() {
        return logs;
    }

    public void setLogs(List<GetFoodResp> logs) {
        this.logs = logs;
    }
}
