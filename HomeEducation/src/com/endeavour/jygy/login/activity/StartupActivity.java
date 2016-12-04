package com.endeavour.jygy.login.activity;

import android.os.Bundle;
import android.view.View;

import com.endeavour.jygy.LoginActivity;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.update.UpdateManager;
import com.flyco.roundview.RoundTextView;

public class StartupActivity extends BaseViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_startup);
        RoundTextView btnLogin = (RoundTextView) findViewById(R.id.btn2Login);
        RoundTextView btnSign = (RoundTextView) findViewById(R.id.btn2Sign);
        btnLogin.setOnClickListener(this);
        btnSign.setOnClickListener(this);
        UpdateManager manager = new UpdateManager(StartupActivity.this);
		// 检查软件更新
		manager.checkUpdate();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn2Login:
                Tools.toActivity(StartupActivity.this, LoginActivity.class);
                StartupActivity.this.finish();
                break;
            case R.id.btn2Sign:
                Tools.toActivity(StartupActivity.this, SignStep2Activity.class);
                StartupActivity.this.finish();
                break;
        }
    }
}
