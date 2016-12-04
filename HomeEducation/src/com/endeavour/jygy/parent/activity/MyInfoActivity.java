package com.endeavour.jygy.parent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;

public class MyInfoActivity extends BaseViewActivity implements View.OnClickListener {

    protected ImageView ivBabyIcon;
    protected TextView tvBabyName;
    protected TextView tvPay;
    protected RelativeLayout llBabySex;
    protected TextView tvBridthDay;
    protected RelativeLayout llBabyBrithDay;
    protected RelativeLayout llPayHistory;

    public static Intent getStartIntent(Context context){
        Intent intent = new Intent(context, MyInfoActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("我的信息");
        showTitleBack();
        setMainView(R.layout.activity_my_info);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvPay) {
            startActivity(PayListActivity.getStartIntent(this));
        } else if (view.getId() == R.id.llBabySex) {

        } else if (view.getId() == R.id.llBabyBrithDay) {

        } else if (view.getId() == R.id.llPayHistory) {
            startActivity(MyPayHistoryActivity.getStartIntent(this));
        }
    }

    private void initView() {
        ivBabyIcon = (ImageView) findViewById(R.id.ivBabyIcon);
        tvBabyName = (TextView) findViewById(R.id.tvBabyName);
        tvPay = (TextView) findViewById(R.id.tvPay);
        tvPay.setOnClickListener(MyInfoActivity.this);
        llBabySex = (RelativeLayout) findViewById(R.id.llBabySex);
        llBabySex.setOnClickListener(MyInfoActivity.this);
        tvBridthDay = (TextView) findViewById(R.id.tvBridthDay);
        llBabyBrithDay = (RelativeLayout) findViewById(R.id.llBabyBrithDay);
        llBabyBrithDay.setOnClickListener(MyInfoActivity.this);
        llPayHistory = (RelativeLayout) findViewById(R.id.llPayHistory);
        llPayHistory.setOnClickListener(MyInfoActivity.this);
    }
}
