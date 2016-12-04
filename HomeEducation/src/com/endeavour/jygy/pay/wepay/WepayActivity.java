package com.endeavour.jygy.pay.wepay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.activity.PaySuccessActivity;
import com.endeavour.jygy.parent.activity.PayTransFlow;
import com.endeavour.jygy.parent.bean.GetPayInfoResp;
import com.endeavour.jygy.parent.bean.PayBody;
import com.endeavour.jygy.pay.wepay.persenter.WePayTransaction;

/**
 * Created by wu on 16/6/5.
 */
public class WepayActivity extends PayTransFlow {

    private boolean canBack = false;

    public static final String EXTRA_PAYINFO = "EXTRA_PAYINFO";

    private WePaySuccessReceiver wePaySuccessReceiver;

    public static Intent getStartIntent(Context context, GetPayInfoResp getPayInfoResp) {
        Intent intent = new Intent(context, WepayActivity.class);
        intent.putExtra(EXTRA_PAYINFO, getPayInfoResp);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("正在支付");
        setMainView(R.layout.activity_pay);
        showTitleBack();

        progresser.showProgress();
        GetPayInfoResp getPayInfoResp = (GetPayInfoResp) getIntent().getSerializableExtra(EXTRA_PAYINFO);
        WePayTransaction wePayTransaction = new WePayTransaction(this);
        PayBody payBody = new PayBody();
        payBody.setUserId(AppConfigHelper.getConfig(AppConfigDef.studentID));
        payBody.setFeeId(getPayInfoResp.getId());
        String attach = JSONObject.toJSONString(payBody);
        wePayTransaction.pay(this, Integer.parseInt(getPayInfoResp.getFeeAmount()), getPayInfoResp.getFeeName(), attach, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                canBack = true;
                regReceiver();
            }

            @Override
            public void onFaild(Response response) {
                canBack = true;
                Toast.makeText(WepayActivity.this, response.msg, Toast.LENGTH_SHORT).show();
                WepayActivity.this.finish();
            }
        });
    }

    private void regReceiver() {
        wePaySuccessReceiver = new WePaySuccessReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(wePaySuccessReceiver, new IntentFilter(WePaySuccessReceiver.WEPAY_RESULT));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wePaySuccessReceiver != null) {
            try {
                this.unregisterReceiver(wePaySuccessReceiver);
            } catch (IllegalArgumentException ignore) {
            }
        }

    }

    private void paySuccess() {
        startActivity(PaySuccessActivity.getStartIntent(WepayActivity.this, PaySuccessActivity.TYEP_WEPAY));
        this.finish();
    }

    public class WePaySuccessReceiver extends BroadcastReceiver {

        public static final String WEPAY_RESULT = "com.endeavour.jygy.pay.wepay.WepaySuccessReceiver.SUCCESS";
        public static final String WEPAY_FAILD = "com.endeavour.jygy.pay.wepay.WepaySuccessReceiver.FAILD";

        @Override
        public void onReceive(Context context, Intent intent) {
            int code = intent.getIntExtra("errCode", -1);
            if (code == WePayTransaction.PAY_SUCCESS) {
                paySuccess();
            } else {
                progresser.showError("支付失败", false);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (canBack) {
            super.onBackPressed();
        }
    }
}
