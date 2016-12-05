package com.endeavour.jygy.parent.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.adapter.StudenMoralTaskListAdapter;
import com.endeavour.jygy.parent.bean.GetStudenTasksResp;
import com.endeavour.jygy.parent.bean.GetStudentTasksReq;
import com.endeavour.jygy.parent.bean.Student;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BabyMoralDetailActvity extends BaseViewActivity {
    private RelativeLayout llBanner;
    private ImageView ivIcon;
    private TextView tvName;
    private TextView tvStudenMoral;
    private ListView lvTasks;

    private StudenMoralTaskListAdapter taskAdapter;
    private Student studen;
    private List<GetStudenTasksResp> getStudenTasksResps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studen = (Student) getIntent().getSerializableExtra("student");
        if (studen == null) {
            this.finish();
            return;
        }
        setMainView(R.layout.activity_baby_moral_detail_actvity);
        setTitleText(studen.getName());
        showTitleBack();
        llBanner = (RelativeLayout) findViewById(R.id.llBanner);
        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        ImageLoader.getInstance().displayImage(studen.getHeadPortrait(), ivIcon, ("1".equals(studen.getSex()) ? MainApp.getBobyOptions() : MainApp.getGrilsOptions()));
        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(studen.getName());
        tvStudenMoral = (TextView) findViewById(R.id.tvStudenMoral);
        String moral = studen.getMoral();
        tvStudenMoral.setText((TextUtils.isEmpty(moral) ? "--" : moral));
        lvTasks = (ListView) findViewById(R.id.lvTasks);
        taskAdapter = new StudenMoralTaskListAdapter(this);
        lvTasks.setAdapter(taskAdapter);
        getTaskList();
    }


    public void getTaskList() {
        progresser.showProgress();
        GetStudentTasksReq req = new GetStudentTasksReq();
        req.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        req.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        req.setStudentId(studen.getStudentId());
        req.setPageNo("1");
        req.setPerNo("999");
        req.setSemester(studen.getSemester());
        req.setGradeLevel(studen.getGradeLevel());
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                getStudenTasksResps = new ArrayList<>();
                List<GetStudenTasksResp> resps = JSONArray.parseArray(String.valueOf(response.getResult()), GetStudenTasksResp.class);
                Iterator<GetStudenTasksResp> iterator = resps.iterator();
                while (iterator.hasNext()) {
                    GetStudenTasksResp resp = iterator.next();
                    if (!TextUtils.isEmpty(resp.getReviewContent())) {
                        getStudenTasksResps.add(resp);
                    }
                }
                if (getStudenTasksResps != null && !getStudenTasksResps.isEmpty()) {
                    taskAdapter.setDataChanged(getStudenTasksResps);
                }
            }

            @Override
            public void onFailed(Response response) {
                progresser.showError(response.getMsg(), false);
            }
        });
    }
}
