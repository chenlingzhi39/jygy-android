package com.endeavour.jygy.parent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.bean.GetAdjustClassApplysResp;
import com.endeavour.jygy.teacher.bean.DisposeAdjustClassApplyReq;

/**
 * Created by wu on 15/11/26.
 */
public class TeachrChangeClassRecordActivity extends BaseViewActivity {

    private static final String LOG_TAG = TeachrChangeClassRecordActivity.class.getSimpleName();


    public static final String EXTRA_CLASS_INFO = "EXTRA_ADJUST_CLASS_APPLY";

    public static Intent getStartIntent(Context context, GetAdjustClassApplysResp resp) {
        Intent intent = new Intent(context, TeachrChangeClassRecordActivity.class);
        intent.putExtra(EXTRA_CLASS_INFO, resp);
        return intent;
    }

    private GetAdjustClassApplysResp resp;

    protected TextView tvBabyName;
    protected TextView tvParentName;
    protected TextView tvLocation;
    protected TextView tvSchool;
    protected TextView tvClass;
    protected TextView tvChooseClass;
    protected Button btnApply;
    protected Button btnCancel;
    protected TextView tvNewClass;
    protected TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resp = (GetAdjustClassApplysResp) getIntent().getSerializableExtra(EXTRA_CLASS_INFO);

        setTitleText("换班申请审核");
        showTitleBack();
        setMainView(R.layout.activity_teacher_change_class_record);
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
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply("1");
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply("2");
            }
        });

        tvNewClass = (TextView) findViewById(R.id.tvNewClass);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvBabyName.setText(resp.getApplyUserName());
//        tvPhoneNum.setText(); //TODO
        tvSchool.setText(resp.getKindergartenName());
        tvLocation.setText(AppConfigHelper.getConfig(AppConfigDef.location));

        tvClass.setText(resp.getKindergartenClassName());
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

    private void apply(String status) {

        progresser.showProgress();
        DisposeAdjustClassApplyReq req = new DisposeAdjustClassApplyReq();
        req.setClassApplyId(resp.getId());
        req.setApprovalStatus(status);
        req.setApprovalUserId(AppConfigHelper.getConfig(AppConfigDef.parentId));
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                Toast.makeText(TeachrChangeClassRecordActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                TeachrChangeClassRecordActivity.this.finish();
            }

            @Override
            public void onFailed(Response response) {
                progresser.showError(response.getMsg(), false);
            }
        });
    }


}
