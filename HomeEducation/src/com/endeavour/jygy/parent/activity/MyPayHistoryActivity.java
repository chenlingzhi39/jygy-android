package com.endeavour.jygy.parent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.adapter.PayHistoryAdapter;
import com.endeavour.jygy.parent.bean.GetMyPayRecordsReq;
import com.endeavour.jygy.parent.bean.GetMyPayRecordsResp;

import java.util.List;

/**
 * 我的缴费历史
 * Created by wu on 16/6/26.
 */
public class MyPayHistoryActivity extends BaseViewActivity {

    protected ListView lvPayHistory;

    private PayHistoryAdapter payHistoryAdapter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MyPayHistoryActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("我的缴费记录");
        showTitleBack();
        setMainView(R.layout.activity_my_pay_history);
        initView();

        payHistoryAdapter = new PayHistoryAdapter(this);
        lvPayHistory.setAdapter(payHistoryAdapter);
        lvPayHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetMyPayRecordsResp item = payHistoryAdapter.getItem(position);
                MyPayHistoryActivity.this.startActivity(PayInfoActivity.getStartIntent(MyPayHistoryActivity.this, item));
            }
        });
        getMyPayRecords();
    }

    private void getMyPayRecords() {
        progresser.showProgress();
        GetMyPayRecordsReq getMyPayRecordsReq = new GetMyPayRecordsReq();
        getMyPayRecordsReq.setStudentId(AppConfigHelper.getConfig(AppConfigDef.studentID));
        NetRequest.getInstance().addRequest(getMyPayRecordsReq, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                List<GetMyPayRecordsResp> getMyPayRecordsRespList = JSONArray.parseArray(String.valueOf(response.getResult()), GetMyPayRecordsResp.class);
                if (getMyPayRecordsRespList != null) {
                    progresser.showContent();
                    payHistoryAdapter.setDataChanged(getMyPayRecordsRespList);
                } else {
                    progresser.showError("暂无数据", false);
                }
            }

            @Override
            public void onFailed(Response response) {
                progresser.showError(response.getMsg(), false);
            }
        });
    }

    private void initView() {
        lvPayHistory = (ListView) findViewById(R.id.lvPayHistory);
    }
}
