package com.endeavour.jygy.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.db.DbHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.teacher.adapter.TeachPlanListAdapter;
import com.endeavour.jygy.teacher.bean.GetLessonPlansReq;
import com.endeavour.jygy.teacher.bean.GetLessonPlansResp;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

/**
 * Created by wu on 15/11/26.
 */
public class TeacherPlanActivity extends BaseViewActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private TeachPlanListAdapter teachAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.teackplan_list);
        setTitleText("老师教案");
        showTitleBack();
        listView = (ListView) findViewById(R.id.data_list);
        teachAdapter = new TeachPlanListAdapter(this);
        listView.setAdapter(teachAdapter);
        listView.setOnItemClickListener(this);
        initdata();
    }

    private void initdata() {
        progresser.showProgress();
        GetLessonPlansResp lastResp = null;
        try {
            lastResp = DbHelper.getInstance().getDbController().findFirst(Selector.from(GetLessonPlansResp.class).orderBy("updateTime", true));
        } catch (DbException e) {
            e.printStackTrace();
        }
        String updateTime = (lastResp == null || TextUtils.isEmpty(lastResp.getUpdateTime())) ? "0" : lastResp.getUpdateTime();
        GetLessonPlansReq req = new GetLessonPlansReq();
        req.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        req.setGradeId(AppConfigHelper.getConfig(AppConfigDef.gradeID));
        req.setUpdateTime(updateTime);
        req.setSemester(AppConfigHelper.getConfig(AppConfigDef.semester));
        req.setGradeLevel(AppConfigHelper.getConfig(AppConfigDef.gradeLevel));
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                List<GetLessonPlansResp> resps = JSONArray.parseArray(String.valueOf(response.getResult()), GetLessonPlansResp.class);
                try {
                    DbHelper.getInstance().getDbController().saveAll(resps);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                showData();
            }

            @Override
            public void onFaild(Response response) {
                progresser.showContent();
                Tools.toastMsg(TeacherPlanActivity.this, response.getMsg());
                showData();
            }
        });
    }

    private void showData() {
        try {
            List<GetLessonPlansResp> resps = DbHelper.getInstance().getDbController().findAll(GetLessonPlansResp.class);
            teachAdapter.setDataChanged(resps);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(TeacherPlanActivity.this, TeachContentActivity.class);
        GetLessonPlansResp resp = teachAdapter.getItem(position);
        intent.putExtra("GetLessonPlansResp", resp);
        startActivity(intent);
    }
}
