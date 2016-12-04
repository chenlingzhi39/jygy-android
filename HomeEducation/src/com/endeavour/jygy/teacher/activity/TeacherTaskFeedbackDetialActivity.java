package com.endeavour.jygy.teacher.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.teacher.bean.GetTeacherTaskFeedbackListResp;
import com.endeavour.jygy.teacher.bean.TeacherGetFeedBackDetailReq;
import com.endeavour.jygy.teacher.bean.TeacherGetFeedBackDetailResp;

/**
 * Created by wu on 15/12/15.
 */
public class TeacherTaskFeedbackDetialActivity extends BaseViewActivity {

    private GetTeacherTaskFeedbackListResp resp;
    private TextView htmlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resp = (GetTeacherTaskFeedbackListResp) getIntent().getSerializableExtra("GetTeacherTaskFeedbackListResp");
        if (resp == null) {
            return;
        }
        setTitleText("任务反馈");
        showTitleBack();
        setMainView(R.layout.activity_teacher_feedback_detail);
        htmlView = (TextView) findViewById(R.id.htmlView);
        initData();
    }

    private void initData() {
        progresser.showProgress();
        TeacherGetFeedBackDetailReq req = new TeacherGetFeedBackDetailReq();
        req.setId(resp.getId());
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                TeacherGetFeedBackDetailResp resp = JSONObject.parseObject(String.valueOf(response.getResult()), TeacherGetFeedBackDetailResp.class);
                htmlView.setText(String.valueOf(resp.getContent()));
            }

            @Override
            public void onFaild(Response response) {
                progresser.showContent();
                Tools.toastMsg(TeacherTaskFeedbackDetialActivity.this, response.getMsg());
            }
        });
    }


}
