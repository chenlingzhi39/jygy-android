package com.endeavour.jygy.schoolboss.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.activity.GradeClassOpter;
import com.endeavour.jygy.parent.bean.GetGradeClassResp;

import java.util.List;

/**
 * Created by wu on 15/12/24.
 */
public class SchoolGradesActivity extends BaseViewActivity {

    private String classTitle;
    private GradeClassOpter opter;

    private String gradeId;
    private String gradeNick;

    private View viewTuo, viewSmall, viewMiddle, viewBig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classTitle = getIntent().getStringExtra("title");
        setTitleText(TextUtils.isEmpty(classTitle) ? "班级信息" : classTitle);
        showTitleBack();
        setMainView(R.layout.activity_my_grades);
        viewTuo = findViewById(R.id.btnTuo);
        viewTuo.setOnClickListener(this);
        viewSmall = findViewById(R.id.btnSmall);
        viewSmall.setOnClickListener(this);
        viewMiddle = findViewById(R.id.btnMiddle);
        viewMiddle.setOnClickListener(this);
        viewBig = findViewById(R.id.btnBig);
        viewBig.setOnClickListener(this);
        opter = new GradeClassOpter();

        getClassesList();
    }


    private void getClassesList() {
        progresser.showProgress();
        opter.doGetGradeClassAction(new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                List<GetGradeClassResp> resps = (List<GetGradeClassResp>) response.getResult();
                for (GetGradeClassResp grade : resps) {
                    String nick = grade.getName();
                    if (nick.contains("大")) {
                        viewBig.setTag(grade);
                    } else if (nick.contains("中")) {
                        viewMiddle.setTag(grade);
                    } else if (nick.contains("小")) {
                        viewSmall.setTag(grade);
                    } else if (nick.contains("托")) {
                        viewTuo.setTag(grade);
                    }
                }
            }

            @Override
            public void onFaild(Response response) {
                progresser.showError(response.getMsg(), false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getTag() == null) {
            return;
        }
        GetGradeClassResp resp = (GetGradeClassResp) v.getTag();
        gradeId = resp.getId();
        gradeNick = resp.getName();
        Intent intent = getIntent();
        intent.setClass(this, SchoolClassesActivity.class);
        intent.putExtra("gradeId", gradeId);
        intent.putExtra("gradeNick", gradeNick);
        startActivityForResult(intent, 10001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10001 && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            this.finish();
        }
    }

//    @Override
//    public void onItemClicked(int position, View v) {
//        if (currentState == 1) {
//            AppConfigHelper.setConfig(AppConfigDef.classID, adapter.getItem(position).getId());
//            AppConfigHelper.setConfig(AppConfigDef.gradeID, gradeId);
//            AppConfigHelper.setConfig(AppConfigDef.gradeNick, gradeNick);
//            AppConfigHelper.setConfig(AppConfigDef.classNick, adapter.getItem(position).getNick());
//            if (TextUtils.isEmpty(classTitle)) {
//                Intent intent = new Intent(this, MyClassGridActivity.class);
//                startActivity(intent);
//            } else {
//
//            }
//        } else {
//            gradeId = adapter.getItem(position).getId();
//            gradeNick = adapter.getItem(position).getNick();
//            adapter.setDataChanged(opter.getClasses(adapter.getItem(position).getId()));
//            currentState = 1;
//        }
//
//    }


}
