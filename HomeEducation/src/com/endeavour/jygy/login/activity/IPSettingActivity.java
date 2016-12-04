package com.endeavour.jygy.login.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.DefaultAppConfigHelper;
import com.endeavour.jygy.common.volley.NetRequest;

/**
 * Created by wu on 15/12/27.
 */
public class IPSettingActivity extends BaseViewActivity {

    private EditText etIP;
    private EditText etPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("IP设置");
        showTitleBack();
        setTitleRight("确定");

        setMainView(R.layout.activity_ip_setting);

        etIP = (EditText) findViewById(R.id.etIP);
        etPort = (EditText) findViewById(R.id.etPort);
        etIP.setText(DefaultAppConfigHelper.getConfig(AppConfigDef.ip));
        etPort.setText(DefaultAppConfigHelper.getConfig(AppConfigDef.port));
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        DefaultAppConfigHelper.setConfig(AppConfigDef.ip, etIP.getText().toString());
        DefaultAppConfigHelper.setConfig(AppConfigDef.port, etPort.getText().toString());
        NetRequest.serverUrl = null;
        this.finish();
    }
}
