package com.endeavour.jygy.parent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.adapter.ChangeClassAdapter;
import com.endeavour.jygy.parent.bean.GetApplyHistoryListReq;
import com.endeavour.jygy.parent.bean.GetApplyHistoryListResp;

import java.util.List;

/**
 * Created by wu on 16/7/6.
 */
public class ChangeClassHistoryActivity extends ChangeClassTransFlow {

    protected ListView lvHistory;

    private ChangeClassAdapter adapter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ChangeClassHistoryActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("换班记录");
        showTitleBack();
        setMainView(R.layout.activity_change_class_history);
        initView();
        getHistory();
    }

    private void getHistory() {
        progresser.showProgress();
        GetApplyHistoryListReq req = new GetApplyHistoryListReq();
        req.setLastTime("");
        req.setStudentId(AppConfigHelper.getConfig(AppConfigDef.studentID));
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                List<GetApplyHistoryListResp> respList = JSONObject.parseArray(String.valueOf(response.getResult()), GetApplyHistoryListResp.class);
                adapter.setDataChanged(respList);
            }

            @Override
            public void onFaild(Response response) {
                progresser.showError(response.getMsg(), false);
            }
        });
    }

    private void initView() {
        lvHistory = (ListView) findViewById(R.id.lvHistory);
        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if("0".equals(adapter.getItem(position).getStatus())){
                    ChangeClassHistoryActivity.this.startActivity(ChangeClassRecordActivity.getStartIntent(ChangeClassHistoryActivity.this, adapter.getItem(position)));
                }
            }
        });
        adapter = new ChangeClassAdapter(this);
        lvHistory.setAdapter(adapter);
    }
}
