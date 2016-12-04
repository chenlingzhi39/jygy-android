package com.endeavour.jygy.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.teacher.bean.GetTeacherTaskResp;
import com.endeavour.jygy.teacher.bean.TeacherFeedbackReq;
import com.endeavour.jygy.teacher.fragment.TeacherTaskListFragment;

/**
 * Created by wu on 15/12/16.
 */
public class TeacherTaskFeedbackActivity extends BaseViewActivity {
    private GetTeacherTaskResp resp;

    private EditText etContent;
    private RatingBar bar;
    private CheckBox ckRecommend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("评分");
        showTitleBack();
        resp = (GetTeacherTaskResp) getIntent().getSerializableExtra("GetTeacherTaskResp");
        if (resp == null) {
            return;
        }
        setTitleRight("发送");
        setMainView(R.layout.activity_teacher_task_feedback);
        etContent = (EditText) findViewById(R.id.etContent);
        bar = (RatingBar) findViewById(R.id.ratingBar);
        ckRecommend = (CheckBox) findViewById(R.id.ckRecommend);
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        String content = etContent.getText().toString();
        int score = (int) bar.getRating();
        if(TextUtils.isEmpty(content)){
            Tools.toastMsg(this, "请填写评分内容");
            return;
        }
        if(score <= 0){
            Tools.toastMsg(this, "请点击星星为宝宝打分");
            return;
        }
        TeacherFeedbackReq req = new TeacherFeedbackReq();
        req.setReviewContent(content);
        req.setId(resp.getId());
        req.setStar(score + "");
        req.setRecommend(ckRecommend.isChecked());
        req.setUserId(AppConfigHelper.getConfig(AppConfigDef.parentId));
        progresser.showProgress();
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                setResult(RESULT_OK);
                LocalBroadcastManager.getInstance(TeacherTaskFeedbackActivity.this).sendBroadcast(new Intent(TeacherTaskListFragment.UpdateTeacherTaskListReceiver.ACTION));
                TeacherTaskFeedbackActivity.this.finish();
            }

            @Override
            public void onFaild(Response response) {
                progresser.showContent();
                Tools.toastMsg(TeacherTaskFeedbackActivity.this, response.getMsg());
            }
        });
    }
}
