package com.endeavour.jygy.teacher.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewFragment;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.db.DbHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.teacher.activity.TeacherTaskDetialActivity;
import com.endeavour.jygy.teacher.adapter.TeachTaskListAdapter;
import com.endeavour.jygy.teacher.bean.GetTeacherTaskReq;
import com.endeavour.jygy.teacher.bean.GetTeacherTaskResp;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

/**
 * Created by wu on 15/12/15.
 */
public class TeacherTaskListFragment extends BaseViewFragment implements AdapterView.OnItemClickListener {

    private TeachTaskListAdapter adapter;
    private ListView lvTeacherTask;
    private List<GetTeacherTaskResp> list;

    public class UpdateTeacherTaskListReceiver extends BroadcastReceiver {
        public static final String ACTION = "com.endeavour.jygy.teacher.fragment.UpdateTeacherTaskList";

        @Override
        public void onReceive(Context context, Intent intent) {
            getTaskList();
        }
    }


    private UpdateTeacherTaskListReceiver updateTeacherTaskListReceiver;

    @Override
    public void initView() {
        setMainView(R.layout.activity_teacher_task_list);
        lvTeacherTask = (ListView) mainView.findViewById(R.id.lvTeacherTask);
        adapter = new TeachTaskListAdapter(getActivity());
        adapter.setDataChanged(list);
        lvTeacherTask.setAdapter(adapter);
        lvTeacherTask.setOnItemClickListener(this);
        updateTeacherTaskListReceiver = new UpdateTeacherTaskListReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(updateTeacherTaskListReceiver, new IntentFilter(UpdateTeacherTaskListReceiver.ACTION));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (updateTeacherTaskListReceiver != null) {
            try {
                LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(updateTeacherTaskListReceiver);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getTaskList();
    }

    public void setDataChanged(List<GetTeacherTaskResp> list) {
        this.list = list;
        if (adapter != null) {
            adapter.setDataChanged(list);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GetTeacherTaskResp resp = adapter.getItem(position);
        resp.setReaded();
        try {
            DbHelper.getInstance().getDbController().update(resp, "isReaded");
        } catch (DbException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getActivity(), TeacherTaskDetialActivity.class);
        intent.putExtra("GetTeacherTaskResp", resp);
        startActivity(intent);
    }

    public void getTaskList() {
        String classId = AppConfigHelper.getConfig(AppConfigDef.classID);
        if (TextUtils.isEmpty(classId)) {
            return;
        }
        progresser.showProgress();
//        GetTeacherTaskResp lastResp = null;
//        try {
//            lastResp = DbHelper.getInstance().getDbController().findFirst(Selector.from(GetTeacherTaskResp.class).where("classId", "=", classId).orderBy("updateTime", true));
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
        GetTeacherTaskReq req = new GetTeacherTaskReq();
        req.setClassId(classId);
        req.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        req.setUpdateTime("0");
//        req.setUpdateTime((lastResp == null || TextUtils.isEmpty(lastResp.getUpdateTime())) ? "0" : lastResp.getUpdateTime());
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                List<GetTeacherTaskResp> resps = JSONArray.parseArray(String.valueOf(response.getResult()), GetTeacherTaskResp.class);
                setDataChanged(resps);
//                try {
//                    DbUtils dbController = DbHelper.getInstance().getDbController();
//                    String classId = AppConfigHelper.getConfig(AppConfigDef.classID);
//                    for(GetTeacherTaskResp resp : resps){
//                        resp.setClassId(classId);
//                        dbController.save(resp);
//                    }
//                    resps = dbController.findAll(Selector.from(GetTeacherTaskResp.class).where("classId", "=", classId));
//                    setDataChanged(resps);
//                } catch (DbException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailed(Response response) {
//                progresser.showContent();
//                Tools.toastMsg(getActivity(), response.getMsg());
                progresser.showError(response.getMsg(), false);
//                showData();
            }
        });
    }

    private void showData() {
        try {
            List<GetTeacherTaskResp> resps = DbHelper.getInstance().getDbController().findAll(Selector.from(GetTeacherTaskResp.class).orderBy("status", false));
            setDataChanged(resps);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
