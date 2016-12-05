package com.endeavour.jygy.teacher.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.teacher.adapter.TeacherCheckAdapter;
import com.endeavour.jygy.teacher.bean.StudentVerityInfosReq;
import com.endeavour.jygy.teacher.bean.StudentVerityInfosResp;
import com.endeavour.jygy.teacher.bean.StudentVerityReq;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import java.util.List;


/**
 * Created by wu on 16/1/13.
 */
public class TeacherCheckActivity extends BaseViewActivity implements TeacherCheckAdapter.TeacherCheckAdapterClickListener {
    private ListView lvTeacherCheck;
    private TeacherCheckAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("宝宝信息审核");
        showTitleBack();
        setMainView(R.layout.activity_teacher_check);
        lvTeacherCheck = (ListView) findViewById(R.id.lvTeacherCheck);
        adapter = new TeacherCheckAdapter(this);
        adapter.setListener(this);
        lvTeacherCheck.setAdapter(adapter);
        progresser.showProgress();
        getListData();
    }

    private void getListData() {
        StudentVerityInfosReq req = new StudentVerityInfosReq();
        req.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        req.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                List<StudentVerityInfosResp> resps = JSONArray.parseArray(String.valueOf(response.getResult()), StudentVerityInfosResp.class);
                adapter.setDatachanged(resps);
            }

            @Override
            public void onFailed(Response response) {
                progresser.showContent();
                progresser.showError(response.getMsg(), false);
            }
        });
    }

    public void doStudentVerityAction(String studentId) {
        progresser.showProgress();
        StudentVerityReq req = new StudentVerityReq();
        req.setStudentId(studentId);
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                getListData();
            }

            @Override
            public void onFailed(Response response) {
                progresser.showContent();
                Tools.toastMsg(TeacherCheckActivity.this, response.getMsg());
            }
        });
    }

    @Override
    public void onCheckClick(final StudentVerityInfosResp resp) {
        final String[] stringItems = {"确认审核"};
        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, findViewById(R.id.rlMain));
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    doStudentVerityAction(resp.getStudentId());
                }
                dialog.dismiss();
            }
        });
    }
}
