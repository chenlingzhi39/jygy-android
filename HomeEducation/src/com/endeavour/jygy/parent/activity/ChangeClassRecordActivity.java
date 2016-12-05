package com.endeavour.jygy.parent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.bean.GetApplyHistoryListResp;
import com.endeavour.jygy.teacher.bean.DisposeAdjustClassApplyReq;

/**
 * Created by wu on 15/11/26.
 */
public class ChangeClassRecordActivity extends ChangeClassTransFlow {

    private static final String LOG_TAG = ChangeClassRecordActivity.class.getSimpleName();


    public static final String EXTRA_CLASS_INFO = "EXTRA_CLASS_INFO";

    public static Intent getStartIntent(Context context, GetApplyHistoryListResp resp) {
        Intent intent = new Intent(context, ChangeClassRecordActivity.class);
        intent.putExtra(EXTRA_CLASS_INFO, resp);
        return intent;
    }

    private GetApplyHistoryListResp resp;

    protected TextView tvBabyName;
    protected TextView tvParentName;
    protected TextView tvLocation;
    protected TextView tvSchool;
    protected TextView tvClass;
    protected TextView tvChooseClass;
    protected Button btnApply;
    protected TextView tvNewClass;
    protected TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resp = (GetApplyHistoryListResp) getIntent().getSerializableExtra(EXTRA_CLASS_INFO);

        setTitleText("换班申请");
        showTitleBack();
        setMainView(R.layout.activity_change_class_record);
        initView();
    }


    private void initView() {
        tvBabyName = (TextView) findViewById(R.id.tv_babyName);
        tvParentName = (TextView) findViewById(R.id.tv_ParentName);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvSchool = (TextView) findViewById(R.id.tvSchool);
        tvClass = (TextView) findViewById(R.id.tvClass);
        tvChooseClass = (TextView) findViewById(R.id.tvChooseClass);
        btnApply = (Button) findViewById(R.id.btnApply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply();
            }
        });

        tvBabyName.setText(AppConfigHelper.getConfig(AppConfigDef.babyName));
        tvParentName.setText(AppConfigHelper.getConfig(AppConfigDef.phoneNum));
        tvSchool.setText(AppConfigHelper.getConfig(AppConfigDef.schoolName));
        tvClass.setText(AppConfigHelper.getConfig(AppConfigDef.className));
        tvLocation.setText(AppConfigHelper.getConfig(AppConfigDef.location));
        tvNewClass = (TextView) findViewById(R.id.tvNewClass);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        tvNewClass.setText(resp.getNewkindergartenClassName());
        //0 :未审核 1：已通过 2 ：拒绝 3 取消申请
        if ("0".equals(resp.getStatus())) {
            tvStatus.setText("未审核");
            tvStatus.setTextColor(getResources().getColor(R.color.tv_status_blue));
        } else if ("1".equals(resp.getStatus())) {
            tvStatus.setText("已通过");
            tvStatus.setTextColor(getResources().getColor(R.color.tv_status_green));
            hideBtn();
        } else if ("2".equals(resp.getStatus())) {
            tvStatus.setText("拒绝");
            tvStatus.setTextColor(getResources().getColor(R.color.orangered));
            hideBtn();
        } else {
            tvStatus.setText("取消申请");
            tvStatus.setTextColor(getResources().getColor(R.color.gray));
            hideBtn();
        }
    }

    private void hideBtn() {
        btnApply.setVisibility(View.GONE);
    }

    private void apply() {

        progresser.showProgress();
        DisposeAdjustClassApplyReq req = new DisposeAdjustClassApplyReq();
        req.setClassApplyId(resp.getId());
        req.setApprovalStatus("3");
        req.setApprovalUserId(AppConfigHelper.getConfig(AppConfigDef.parentId));
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                Toast.makeText(ChangeClassRecordActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                ChangeClassRecordActivity.this.finish();
                finishTransFlowActivities();
            }

            @Override
            public void onFailed(Response response) {
                progresser.showError(response.getMsg(), false);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
