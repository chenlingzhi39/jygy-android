package com.endeavour.jygy.parent.activity;

import com.alibaba.fastjson.JSONObject;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.db.DbHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.bean.Dynamic;
import com.endeavour.jygy.parent.bean.DynamicDiscuss;
import com.endeavour.jygy.parent.bean.GetDynamicReq;
import com.endeavour.jygy.parent.bean.GetDynamicResp;
import com.endeavour.jygy.parent.bean.SendDynamicDiscussReq;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.SqlInfo;
import com.lidroid.xutils.db.table.DbModel;
import com.lidroid.xutils.exception.DbException;
import com.wizarpos.log.util.LogEx;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/12/3.
 */
public class DynamicOpter {

    public interface LoadDynamicListener {

        void onLoad(List<Dynamic> dynamics);

    }

    private final int perNo = 15;

    private int pageIndex = 1;

    public void loadMoreDynamics(boolean isWunderfulTime, LoadDynamicListener listener) {
        pageIndex++;
        loadMoreDynamicFromNet(isWunderfulTime, listener);
    }

    public boolean isNoMorePage() {
        return false;
    }

    /**
     * 刷新动态
     *
     * @param listener
     */
    public void refreshDynamics(boolean isWunderfulTime, LoadDynamicListener listener) {
        pageIndex = 1;
        refreshDynamicFromNet(isWunderfulTime, listener);
    }

    /**
     * 获取最旧的一条的时间戳
     *
     * @return
     */
    private String getOldestDynamicTime() {
        try {
            SqlInfo info = new SqlInfo("select createTime from Dynamic8 order by createTime ASC limit 1");
            DbModel last = DbHelper.getInstance().getDbController().findDbModelFirst(info);
            return last.getString("createTime");
        } catch (DbException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取最新的一条动态的时间戳
     *
     * @return
     */
    private String getNewestDynamicTime() {
        try {
            SqlInfo info = new SqlInfo("select createTime from Dynamic8 order by createTime DESC limit 1");
            DbModel last = DbHelper.getInstance().getDbController().findDbModelFirst(info);
            if (last == null) {
                return "";
            }
            return last.getString("createTime");
        } catch (DbException e) {
            e.printStackTrace();
        }
        return "";
    }

    private List<Dynamic> getDynamicsFromDBByPageIndex(int pageIndex) {
        try {
            List<Dynamic> dynamics = new ArrayList<>();
            List<DbModel> dynamicsModel = DbHelper.getInstance().getDbController().findDbModelAll(Selector.from(Dynamic.class).groupBy("json").orderBy("createTime", true).groupBy("json").limit(perNo).offset(perNo * (pageIndex - 1)));
            if (dynamicsModel != null) {
                for (DbModel dataMap : dynamicsModel) {
                    Dynamic dynamic = JSONObject.parseObject(dataMap.getString("json"), Dynamic.class);
                    dynamics.add(dynamic);
                }
            }
            return dynamics;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getDynamicsCount() {
        try {
            SqlInfo info = new SqlInfo("select count(*) from ");
            DbModel count = DbHelper.getInstance().getDbController().findDbModelFirst(info);
            if (count == null) {
                return 0;
            }
            return count.getInt("count(*)");
        } catch (DbException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 从服务端获取最新的记录
     *
     * @param listener
     */
    private void refreshDynamicFromNet(boolean isWunderfulTime, LoadDynamicListener listener) {
        doGetDynamicsFromNetAction(getNewestDynamicTime(), true, isWunderfulTime, listener);
    }

    /**
     * 从服务端获取加载之前的记录
     *
     * @param listener
     */
    private void loadMoreDynamicFromNet(boolean isWunderfulTime, LoadDynamicListener listener) {
        doGetDynamicsFromNetAction(getOldestDynamicTime(), false, isWunderfulTime, listener);
    }

    private void doGetDynamicsFromNetAction(String time, final boolean isReresh, boolean isWunderfulTime, final LoadDynamicListener listener) {
        GetDynamicReq req = new GetDynamicReq();
        if (isWunderfulTime) {
            req.setClassId("-1");
        } else {
            req.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        }
        req.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        req.setPerNo(String.valueOf(perNo));
        req.setRefreshType(isReresh ? GetDynamicReq.refresh : GetDynamicReq.loadMore);
        if (!isReresh) {
            req.setLastTime(time);
        }
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                if (isReresh) {
                    try {
                        DbHelper.getInstance().getDbController().dropTable(Dynamic.class);//清空数据
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
                GetDynamicResp resp = JSONObject.parseObject(String.valueOf(response.getResult()), GetDynamicResp.class);
                List<Dynamic> dynamics = resp.getLogs();
                if (dynamics != null && !dynamics.isEmpty()) {
                    try {
                        org.json.JSONObject respJSON = new org.json.JSONObject(String.valueOf(response.getResult()));
                        org.json.JSONArray logsJSON = respJSON.getJSONArray("logs");
                        for (int i = 0; i < dynamics.size(); i++) {
                            dynamics.get(i).setJson(logsJSON.getString(i));
                            DbHelper.getInstance().getDbController().save(dynamics.get(i));//保存
                        }
                    } catch (DbException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    LogEx.d("dynamic", "no new dynamics");
                }
                listener.onLoad(getDynamicsFromDBByPageIndex(pageIndex));
            }

            @Override
            public void onFaild(Response response) {
                listener.onLoad(getDynamicsFromDBByPageIndex(pageIndex));
            }
        });
    }

    public void like(Dynamic dynamic, final BaseRequest.ResponseListener listener) {
        SendDynamicDiscussReq req = new SendDynamicDiscussReq();
        req.setUserId(AppConfigHelper.getConfig(AppConfigDef.parentId));
        req.setMsgId(dynamic.getId());
        req.setType("1");
        req.setReStudentId(AppConfigHelper.getConfig(AppConfigDef.studentID));
        req.setBeStudentId(dynamic.getStudentId());
        req.setBeUserId(dynamic.getUserId());
        NetRequest.getInstance().addRequest(req, listener);
    }

    public boolean isSelfLiked(Dynamic dynamic) {
        List<DynamicDiscuss> msgLikes = dynamic.getMsgLikes();
        if (msgLikes == null || msgLikes.isEmpty()) {
            return false;
        }
        for (DynamicDiscuss discuss : msgLikes) {
            String reUserId = discuss.getReUserId(); //点赞者的id
            if (AppConfigHelper.getConfig(AppConfigDef.parentId).equals(reUserId)) {
                return true;
            }
        }
        return false;
    }


}
