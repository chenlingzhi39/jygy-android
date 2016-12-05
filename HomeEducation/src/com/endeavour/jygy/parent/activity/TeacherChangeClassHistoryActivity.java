package com.endeavour.jygy.parent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.adapter.TeacherChangeClassAdapter;
import com.endeavour.jygy.parent.bean.GetAdjustClassApplysReq;
import com.endeavour.jygy.parent.bean.GetAdjustClassApplysResp;

import java.util.List;

/**
 * Created by wu on 16/7/6.
 */
public class TeacherChangeClassHistoryActivity extends BaseViewActivity {

    protected ListView lvHistory;

    private TeacherChangeClassAdapter adapter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, TeacherChangeClassHistoryActivity.class);
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
        GetAdjustClassApplysReq req = new GetAdjustClassApplysReq();
        req.setLastTime("");
        req.setTeacherId(AppConfigHelper.getConfig(AppConfigDef.parentId));
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                List<GetAdjustClassApplysResp> respList = JSONObject.parseArray(String.valueOf(response.getResult()), GetAdjustClassApplysResp.class);
                adapter.setDataChanged(respList);
            }

            @Override
            public void onFailed(Response response) {
                progresser.showError(response.getMsg(), false);
            }
        });
    }

    private void initView() {
        lvHistory = (ListView) findViewById(R.id.lvHistory);
        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TeacherChangeClassHistoryActivity.this.startActivity(TeachrChangeClassRecordActivity.getStartIntent(TeacherChangeClassHistoryActivity.this, adapter.getItem(position)));
                TeacherChangeClassHistoryActivity.this.finish();
            }
        });
        adapter = new TeacherChangeClassAdapter(this);
        lvHistory.setAdapter(adapter);
    }
}
