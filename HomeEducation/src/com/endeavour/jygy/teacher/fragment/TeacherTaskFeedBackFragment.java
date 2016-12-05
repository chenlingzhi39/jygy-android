package com.endeavour.jygy.teacher.fragment;

import android.content.Intent;
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
import com.endeavour.jygy.teacher.activity.TeacherTaskFeedbackDetialActivity;
import com.endeavour.jygy.teacher.adapter.TeacherTaskFeedBackAdapter;
import com.endeavour.jygy.teacher.bean.GetTeacherTaskFeedbackListReq;
import com.endeavour.jygy.teacher.bean.GetTeacherTaskFeedbackListResp;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

/**
 * Created by wu on 15/11/26.
 */
public class TeacherTaskFeedBackFragment extends BaseViewFragment implements AdapterView.OnItemClickListener {
    private ListView lvTaskFeedBacks;
    private TeacherTaskFeedBackAdapter adapter;

    public TeacherTaskFeedBackFragment() {
    }

    @Override
    public void initView() {
        setMainView(R.layout.fragment_teacher_task_feedback);
        lvTaskFeedBacks = (ListView) mainView.findViewById(R.id.lvFeedbacks);
        adapter = new TeacherTaskFeedBackAdapter(getActivity());
        lvTaskFeedBacks.setAdapter(adapter);
        lvTaskFeedBacks.setOnItemClickListener(this);
        initdata();
    }

    private void initdata() {
        getTaskFeedback();
    }

    public void getTaskFeedback() {
        final String classId = AppConfigHelper.getConfig(AppConfigDef.classID);
        if (TextUtils.isEmpty(classId)) {
            return;
        }
        GetTeacherTaskFeedbackListResp lastResp = null;
        try {
            lastResp = DbHelper.getInstance().getDbController().findFirst(Selector.from(GetTeacherTaskFeedbackListResp.class).where("classId", "=", classId).orderBy("lastTime", true));
        } catch (DbException e) {
            e.printStackTrace();
        }
//        String updateTime = (lastResp == null || TextUtils.isEmpty(lastResp.getLastTime())) ? "0" : lastResp.getLastTime();
        GetTeacherTaskFeedbackListReq req = new GetTeacherTaskFeedbackListReq();
        req.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        req.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        req.setUpdateTime("0");
        progresser.showProgress();
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                List<GetTeacherTaskFeedbackListResp> resps = JSONArray.parseArray(String.valueOf(response.getResult()), GetTeacherTaskFeedbackListResp.class);
                setDataChanged(resps);
                progresser.showContent();
            }

            @Override
            public void onFailed(Response response) {
                progresser.showError(response.getMsg(), false);
            }
        });
    }

    public void setDataChanged(List<GetTeacherTaskFeedbackListResp> list) {
        if (adapter != null) {
            adapter.setDataChanged(list);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GetTeacherTaskFeedbackListResp resp = adapter.getItem(position);
        resp.setReaded();
//        try {
//            DbHelper.getInstance().getDbController().update(resp, "isReaded");
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
        Intent intent = new Intent(getActivity(), TeacherTaskFeedbackDetialActivity.class);
        intent.putExtra("GetTeacherTaskFeedbackListResp", resp);
        startActivity(intent);
    }

}
