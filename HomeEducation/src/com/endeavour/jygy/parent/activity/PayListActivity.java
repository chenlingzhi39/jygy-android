package com.endeavour.jygy.parent.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.adapter.PayListAdapter;
import com.endeavour.jygy.parent.bean.GetPayInfoReq;
import com.endeavour.jygy.parent.bean.GetPayInfoResp;

import java.util.List;

public class PayListActivity extends PayTransFlow {

    protected ListView lvPayHistory;
    private PayListAdapter payListAdapter;
    private PaySuccessReceiver paySuccessReceiver;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, PayListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("资费项目");
        setTitleRight("缴费记录");
        showTitleBack();
        setMainView(R.layout.activity_my_pay_history);
        initView();
        payListAdapter = new PayListAdapter(this);
        payListAdapter.setPayListener(new PayListAdapter.PayListener() {
            @Override
            public void onPay(GetPayInfoResp getPayInfoResp) {
                doPayAction(getPayInfoResp);
            }
        });
        lvPayHistory.setAdapter(payListAdapter);


        paySuccessReceiver = new PaySuccessReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(paySuccessReceiver, new IntentFilter(PaySuccessReceiver.PAY_SUCCESS));

        if ("1".equals(AppConfigHelper.getConfig(AppConfigDef.graduationFlag))) {
            progresser.showError("您的宝宝已毕业, 无法操作", false);
        } else {
            getPayInfos();
        }

    }

    private void getPayInfos() {
        progresser.showProgress();
        final GetPayInfoReq getPayInfoReq = new GetPayInfoReq();
        getPayInfoReq.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        getPayInfoReq.setStudentId(AppConfigHelper.getConfig(AppConfigDef.studentID));
        NetRequest.getInstance().addRequest(getPayInfoReq, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                List<GetPayInfoResp> getPayInfoRespList = JSONObject.parseArray(String.valueOf(response.getResult()), GetPayInfoResp.class);
                if (getPayInfoRespList == null || getPayInfoRespList.isEmpty()) {
                    progresser.showError("暂无缴费项目或已经完成缴费", true);
                } else {
                    payListAdapter.setDataChanged(getPayInfoRespList);
                    progresser.showContent();
                }
                setTitleRight("缴费记录");
            }

            @Override
            public void onFailed(Response response) {
                progresser.showError(response.getMsg(), false);
                setTitleRight("缴费记录");
            }
        });
    }

    private void initView() {
        lvPayHistory = (ListView) findViewById(R.id.lvPayHistory);
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        startActivity(MyPayHistoryActivity.getStartIntent(this));
    }

    private void doPayAction(GetPayInfoResp getPayInfoResp) {
        startActivity(PayInfoActivity.getStartIntent(this, getPayInfoResp));
    }

    public class PaySuccessReceiver extends BroadcastReceiver {

        public static final String PAY_SUCCESS = "com.endeavour.jygy.parent.activity.PayListActivity.PaySuccessReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            getPayInfos();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(paySuccessReceiver);
        } catch (Exception ignored) {

        }
    }
}
