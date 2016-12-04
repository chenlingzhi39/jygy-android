package com.endeavour.jygy.parent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.parent.bean.GetPayInfoResp;
import com.endeavour.jygy.pay.wepay.WepayActivity;

public class ConfirmPayActivity extends PayTransFlow implements View.OnClickListener {

    protected RelativeLayout llPrice;
    protected TextView tvParentName;
    protected LinearLayout llBabyPayInfo;
    protected TextView tvBabyName;
    protected TextView tvPhone;
    protected TextView tvLocation;
    protected TextView tvSchool;
    protected TextView tvClass;
    protected TextView tvClassContent;
    protected Button btnWepay;
    protected Button btnAlipay;

    public static final String EXTRA_PAYINFO = "EXTRA_PAYINFO";

    private GetPayInfoResp getPayInfoResp;

    public static Intent getStartIntent(Context context, GetPayInfoResp getPayInfoResp) {
        Intent intent = new Intent(context, ConfirmPayActivity.class);
        intent.putExtra(EXTRA_PAYINFO, getPayInfoResp);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPayInfoResp = (GetPayInfoResp) getIntent().getSerializableExtra(EXTRA_PAYINFO);
        setTitleText("确认缴费");
        showTitleBack();
        setMainView(R.layout.activity_confirm_pay);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnWepay) {
            startActivity(WepayActivity.getStartIntent(this, getPayInfoResp));
            this.finish();
        } else if (view.getId() == R.id.btnAlipay) {
            startActivity(AlipayActivity.getStartIntent(this, getPayInfoResp));
            this.finish();
        }
    }

    private void initView() {
        llPrice = (RelativeLayout) findViewById(R.id.llPrice);
        tvParentName = (TextView) findViewById(R.id.tvParentName);
        llBabyPayInfo = (LinearLayout) findViewById(R.id.llBabyPayInfo);
        tvBabyName = (TextView) findViewById(R.id.tvBabyName);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvSchool = (TextView) findViewById(R.id.tvSchool);
        tvClass = (TextView) findViewById(R.id.tvClass);
        tvClassContent = (TextView) findViewById(R.id.tvClassContent);
        btnWepay = (Button) findViewById(R.id.btnWepay);
        btnWepay.setOnClickListener(ConfirmPayActivity.this);
        btnAlipay = (Button) findViewById(R.id.btnAlipay);
        btnAlipay.setOnClickListener(ConfirmPayActivity.this);

        tvBabyName.setText(AppConfigHelper.getConfig(AppConfigDef.babyName));
        tvClass.setText(AppConfigHelper.getConfig(AppConfigDef.className));
        tvClassContent.setText(getPayInfoResp.getFeeName());
        tvLocation.setText(AppConfigHelper.getConfig(AppConfigDef.location));
        tvPhone.setText(AppConfigHelper.getConfig(AppConfigDef.phoneNum));
        tvSchool.setText(AppConfigHelper.getConfig(AppConfigDef.schoolName));
        tvParentName.setText(AppConfigHelper.getConfig(AppConfigDef.parentName));
    }
}
