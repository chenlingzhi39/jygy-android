package com.endeavour.jygy.parent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Calculater;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.parent.bean.GetMyPayRecordsResp;
import com.endeavour.jygy.parent.bean.GetPayInfoResp;
import com.flyco.roundview.RoundTextView;

public class PayInfoActivity extends PayTransFlow implements View.OnClickListener {

    protected TextView tvPayAmount;
    protected RelativeLayout llPrice;
    protected TextView tvParentName;
    protected TextView tvBabyName;
    protected TextView tvPayInfo;
    protected LinearLayout llBabySex;
    protected RoundTextView btnPay;

    private GetPayInfoResp getPayInfoResp;
    private GetMyPayRecordsResp myPayRecordsResp;


    public static final String EXTRA_PAYINFO = "EXTRA_PAYINFO";
    public static final String EXTRA_PAYRECORDS = "EXTRA_PAYRECORDS";

    public static Intent getStartIntent(Context context, GetPayInfoResp getPayInfoResp) {
        Intent intent = new Intent(context, PayInfoActivity.class);
        intent.putExtra(EXTRA_PAYINFO, getPayInfoResp);
        return intent;
    }

    public static Intent getStartIntent(Context context, GetMyPayRecordsResp myPayRecordsResp) {
        Intent intent = new Intent(context, PayInfoActivity.class);
        intent.putExtra(EXTRA_PAYRECORDS, myPayRecordsResp);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPayInfoResp = (GetPayInfoResp) getIntent().getSerializableExtra(EXTRA_PAYINFO);
        myPayRecordsResp = (GetMyPayRecordsResp) getIntent().getSerializableExtra(EXTRA_PAYRECORDS);
        setTitleText("确认缴费");
        showTitleBack();
        setMainView(R.layout.activity_pay_info);
        initView();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnPay) {
            startActivity(ConfirmPayActivity.getStartIntent(this, getPayInfoResp));
            this.finish();
        }
    }

    private void initView() {
        tvPayAmount = (TextView) findViewById(R.id.tvPayAmount);
        llPrice = (RelativeLayout) findViewById(R.id.llPrice);
        tvParentName = (TextView) findViewById(R.id.tvParentName);
        tvBabyName = (TextView) findViewById(R.id.tvBabyName);
        tvPayInfo = (TextView) findViewById(R.id.tvPayInfo);
        llBabySex = (LinearLayout) findViewById(R.id.llBabySex);
        btnPay = (RoundTextView) findViewById(R.id.btnPay);
        btnPay.setOnClickListener(PayInfoActivity.this);

        if (getPayInfoResp != null) {
            tvPayAmount.setText("¥ " + Calculater.formotFen(getPayInfoResp.getFeeAmount()));
            tvBabyName.setText(AppConfigHelper.getConfig(AppConfigDef.babyName));
            tvParentName.setText(AppConfigHelper.getConfig(AppConfigDef.parentName));
            tvPayInfo.setText(getPayInfoResp.getDescn());
            btnPay.setVisibility(View.VISIBLE);
        } else if (myPayRecordsResp != null) {
            tvPayAmount.setText("¥ " + Calculater.formotFen(myPayRecordsResp.getTranAmount()));
            tvBabyName.setText(AppConfigHelper.getConfig(AppConfigDef.babyName));
            tvParentName.setText(AppConfigHelper.getConfig(AppConfigDef.parentName));
            tvPayInfo.setText(myPayRecordsResp.getFeeChannels().getDescn());
            btnPay.setVisibility(View.GONE);
        }

    }
}
