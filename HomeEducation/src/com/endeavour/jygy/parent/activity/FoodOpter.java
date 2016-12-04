package com.endeavour.jygy.parent.activity;

import com.alibaba.fastjson.JSONObject;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.bean.GetFoodHistoryReq;
import com.endeavour.jygy.parent.bean.GetFoodReq;
import com.endeavour.jygy.parent.bean.GetFoodResp;
import com.endeavour.jygy.parent.bean.HistoryFoodResp;

import java.util.List;

/**
 * Created by wu on 15/12/5.
 */
public class FoodOpter {

    private int pageIndex = 0;
    public static final int PRENO = 15;

    public interface GetFoodRespListener {
        void onLoad(List<GetFoodResp> resps);

        void onLoad(GetFoodResp resps);

        void onFaild(String msg);
    }

    public void loadMore(final GetFoodRespListener listener) {
        pageIndex++;
        doGetFoodAction(listener);
    }

    public void refresh(final GetFoodRespListener listener) {
        pageIndex = 1;
        doGetFoodAction(listener);
    }

    private void doGetFoodAction(final GetFoodRespListener listener) {
        GetFoodHistoryReq req = new GetFoodHistoryReq();
        req.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        req.setPerNo(PRENO);
        req.setPageNo(pageIndex);
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                HistoryFoodResp history = JSONObject.parseObject(String.valueOf(response.getResult()), HistoryFoodResp.class);
                List<GetFoodResp> resp = history.getLogs();
                listener.onLoad(resp);
            }

            @Override
            public void onFaild(Response response) {
                listener.onFaild(response.getMsg());
            }
        });
    }

    public void doGetCurrentFoodAction(final GetFoodRespListener listener) {
        GetFoodReq req = new GetFoodReq();
        req.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                GetFoodResp resp = JSONObject.parseObject(String.valueOf(response.getResult()), GetFoodResp.class);
                listener.onLoad(resp);
            }

            @Override
            public void onFaild(Response response) {
                listener.onFaild(response.getMsg());
            }
        });
    }
}
