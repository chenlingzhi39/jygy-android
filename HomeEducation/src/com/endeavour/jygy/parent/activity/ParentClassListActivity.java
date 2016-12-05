package com.endeavour.jygy.parent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.alibaba.fastjson.JSONArray;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.adapter.ParentClassAdapter;
import com.endeavour.jygy.parent.bean.GetParentLessonPracticeDet;
import com.endeavour.jygy.parent.bean.GetParentLessonReq;
import com.endeavour.jygy.parent.bean.GetParentLessonResp;

import java.util.List;

public class ParentClassListActivity extends BaseViewActivity {

    private ExpandableListView mListView;
    private ParentClassAdapter adapter;

    public static final String EXTRA_LESSON_PLANID = "EXTRA_LESSON_PLANID";

    public static Intent getStartIntent(Context context, String lessonPlanId) {
        Intent intent = new Intent(context, ParentClassListActivity.class);
        intent.putExtra(EXTRA_LESSON_PLANID, lessonPlanId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitleText("共育课堂");
        showTitleBack();
        setMainView(R.layout.fragment_parent_class_list);
        mListView = (ExpandableListView) findViewById(R.id.lvParentClass);
        adapter = new ParentClassAdapter(this);
        mListView.setAdapter(adapter);
        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                GetParentLessonPracticeDet det = adapter.getChild(groupPosition, childPosition);
                Intent intent = new Intent(ParentClassListActivity.this, ParentClassDetailActivity.class);
                intent.putExtra("det", det);
                startActivity(intent);
                return false;
            }
        });
        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        initData();
    }

    private void initData() {
        progresser.showProgress();
        Log.d(TAG, "initData: " + "lesson/getPractices");
        GetParentLessonReq req = new GetParentLessonReq();
        req.setStudentId(AppConfigHelper.getConfig(AppConfigDef.studentID));
        req.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        req.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        req.setSemester(AppConfigHelper.getConfig(AppConfigDef.semester));
        req.setGradeLevel(AppConfigHelper.getConfig(AppConfigDef.gradeLevel));
        req.setLessonPlanId(getIntent().getStringExtra(EXTRA_LESSON_PLANID));
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                List<GetParentLessonResp> resps = JSONArray.parseArray(String.valueOf(response.getResult()), GetParentLessonResp.class);
                if (resps == null || resps.isEmpty()) {
                    progresser.showError("暂无数据", false);
                } else {
                    adapter.setDataChanged(resps);
                }
                int groupCount = mListView.getCount();
                for (int i = 0; i < groupCount; i++) {
                    mListView.expandGroup(i);
                }
            }

            @Override
            public void onFailed(Response response) {
                progresser.showContent();
                progresser.showError(response.getMsg(), false);
            }
        });
    }


}
