package com.endeavour.jygy.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSONArray;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.activity.BabyMoralDetailActvity;
import com.endeavour.jygy.parent.bean.Student;
import com.endeavour.jygy.teacher.bean.GetStudenList2Req;
import com.endeavour.jygy.teacher.bean.GetStudenList2Resp;

import org.askerov.dynamicgrid.CheeseDynamicAdapter;
import org.askerov.dynamicgrid.DynamicGridView;

import java.util.List;

public class MyClassGridActivity extends BaseViewActivity {

    private static final String TAG = MyClassGridActivity.class.getName();

    private DynamicGridView gridView;
    private CheeseDynamicAdapter adapter;
    private List<GetStudenList2Resp> resps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_grid);
        setTitleText(AppConfigHelper.getConfig(AppConfigDef.classNick));
        showTitleBack();
        initView();
        initlistener();
        initData2();
    }

    private void initData2() {
        progresser.showContent();
        GetStudenList2Req req = new GetStudenList2Req();
        req.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                resps = JSONArray.parseArray(String.valueOf(response.getResult()), GetStudenList2Resp.class);
                if (resps == null || resps.isEmpty()) {
                    progresser.showError("暂无数据", false);
                } else {
                    adapter = new CheeseDynamicAdapter(MyClassGridActivity.this, resps, getResources().getInteger(R.integer.column_count));
                    setTitleText(AppConfigHelper.getConfig(AppConfigDef.classNick)+"("+"人)");
                    gridView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailed(Response response) {
                progresser.showError(response.getMsg(), false);
            }
        });
    }

    private void initView() {
        gridView = (DynamicGridView) findViewById(R.id.dynamic_grid);
        gridView.setEditModeEnabled(false);
    }

    private void initlistener() {
//        gridView.setOnDragListener(new DynamicGridView.OnDragListener() {
//            @Override
//            public void onDragStarted(int position) {
//            }
//
//            @Override
//            public void onDragPositionsChanged(int oldPosition, int newPosition) {
//            }
//        });
//        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view,
//                                           int position, long id) {
//                gridView.startEditMode(position);
//                return true;
//            }
//        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetStudenList2Resp getStudenList2Resp = resps.get(position);
                if (TextUtils.isEmpty(getIntent().getStringExtra("title"))) {
                    GetStudenList2Resp resp = getStudenList2Resp;
                    Student studen = new Student();
                    studen.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
                    studen.setGradeId(AppConfigHelper.getConfig(AppConfigDef.gradeID));
                    studen.setMoral(resp.getMoral());
                    studen.setStudentId(resp.getId());
                    studen.setName(resp.getUserName());
                    studen.setGradeLevel(resp.getGradeLevel());
                    studen.setSex(resp.getSex());
                    studen.setHeadPortrait(resp.getHeadPortrait());
                    studen.setSemester(resp.getSemester());
                    Intent intent = new Intent(MyClassGridActivity.this, BabyMoralDetailActvity.class);
                    intent.putExtra("student", studen);
                    startActivity(intent);
                } else {
                    AppConfigHelper.setConfig(AppConfigDef.studentID, getStudenList2Resp.getId());
                    AppConfigHelper.setConfig(AppConfigDef.babySex, getStudenList2Resp.getSex());
                    AppConfigHelper.setConfig(AppConfigDef.babyName, getStudenList2Resp.getUserName());
                    AppConfigHelper.setConfig(AppConfigDef.growup_userid, getStudenList2Resp.getOtherId());
                    setResult(RESULT_OK);
                    MyClassGridActivity.this.finish();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (gridView.isEditMode()) {
            gridView.stopEditMode();
        } else {
            super.onBackPressed();
        }
    }
}
