package com.endeavour.jygy.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.endeavour.jygy.R;
import com.endeavour.jygy.pay.wepay.WePayConfig;
import com.endeavour.jygy.pay.wepay.WepayActivity;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 微信支付回调
 * Created by wu on 16/6/5.
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String LOG_TAG = WXPayEntryActivity.class.getSimpleName();

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wepay_result);

        api = WXAPIFactory.createWXAPI(this, WePayConfig.WEPAY_APPID, false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(LOG_TAG, "onPayFinish, errCode = " + resp.errCode);
        Intent intent = new Intent(WepayActivity.WePaySuccessReceiver.WEPAY_RESULT);
        intent.putExtra("errCode", resp.errCode);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        this.finish();
    }
}