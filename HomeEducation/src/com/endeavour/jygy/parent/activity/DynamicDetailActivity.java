package com.endeavour.jygy.parent.activity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.bean.Dynamic;

/**
 * 动态明细
 * Created by wu on 15/12/5.
 */
public class DynamicDetailActivity extends BaseViewActivity {

    private Dynamic dynamic;


    private ImageView ivIcon;
    private LinearLayout lvRight;
    private TextView tvName;
    private TextView tvContent;
    private FrameLayout igDynamicViewCon;
    private TextView tvTime;
    private TextView btnDiscuss;
    private ListView lvDiscuss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("动态明细");
        showTitleBack();
        setMainView(R.layout.activity_dynamic_detial);

        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        tvName = (TextView) findViewById(R.id.tvName);
        tvContent = (TextView) findViewById(R.id.tvContent);
        igDynamicViewCon = (FrameLayout) findViewById(R.id.igDynamicViewCon);
        tvTime = (TextView) findViewById(R.id.tvTime);
        btnDiscuss = (TextView) findViewById(R.id.btnDiscuss);
        lvDiscuss = (ListView) findViewById(R.id.lvDiscuss);

        DynamicDetialAdapter adapter =new DynamicDetialAdapter(this);
        lvDiscuss.setAdapter(adapter);

        dynamic = (Dynamic) getIntent().getSerializableExtra("dynamic");
        getDynamicDetail();

    }

    private void getDynamicDetail() {
        progresser.showProgress();
        GetDynamicDiscussReq req = new GetDynamicDiscussReq();
        req.setMsgId(dynamic.getId());
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
            }

            @Override
            public void onFailed(Response response) {
                progresser.showContent();
            }
        });
    }
}
