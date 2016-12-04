package com.endeavour.jygy.parent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.endeavour.jygy.R;

/**
 * Created by wu on 16/6/26.
 */
public class PaySuccessActivity extends PayTransFlow {

    protected ImageView imageView;
    protected TextView tvPaySuccess;
    protected Button btnBack;

    public static final int TYEP_WEPAY = 0;
    public static final int TYEP_ALIPAY = 1;
    private static final String EXTRA_TYPE = "EXTRA_TYPE";

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            PaySuccessActivity.this.finish();
        }
    };

    Handler handler;

    public static Intent getStartIntent(Context context, int type) {
        Intent intent = new Intent(context, PaySuccessActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("支付成功");
        super.setContentView(R.layout.activity_pay_success);
        initView();
        int type = getIntent().getIntExtra(EXTRA_TYPE, TYEP_ALIPAY);
        if (type == TYEP_ALIPAY) {
            imageView.setBackgroundResource(R.drawable.success_zfb);
        } else {
            imageView.setBackgroundResource(R.drawable.success_weixin);
        }
        handler = new Handler();
        handler.postDelayed(runnable, 5 * 1000);
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(PayListActivity.PaySuccessReceiver.PAY_SUCCESS));
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageView);
        tvPaySuccess = (TextView) findViewById(R.id.tvPaySuccess);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaySuccessActivity.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finishTransFlowActivities();
        handler.removeCallbacks(runnable);
        this.handler = null;
    }
}
