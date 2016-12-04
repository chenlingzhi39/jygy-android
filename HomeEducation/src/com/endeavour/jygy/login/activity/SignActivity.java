package com.endeavour.jygy.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.endeavour.jygy.LoginActivity;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Constants;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.login.activity.bean.MsgCodeReq;
import com.endeavour.jygy.login.activity.bean.MsgCodeResp;
import com.endeavour.jygy.login.activity.bean.RegisterReq;
import com.flyco.roundview.RoundTextView;

public class SignActivity extends BaseViewActivity {

    private android.widget.EditText etPhoneNum;
    private android.widget.EditText etAuthCode;
    private android.widget.EditText etPassword;
    private android.widget.EditText etPasswordConfim;
    private com.flyco.roundview.RoundTextView btnNext;
    private RoundTextView btnGetCode;

    private int step = 60;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                step--;
                if (step <= 0) {
                    step = 60;
                    btnGetCode.setText("获取验证码");
                    btnGetCode.setEnabled(true);
                } else {
                    btnGetCode.setText(step + "");
                    handler.postDelayed(runnable, 1 * 1000);
                }
            }
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_sign);
        setTitleText("注册");
        this.btnNext = (RoundTextView) findViewById(R.id.btnNext);
        this.etPasswordConfim = (EditText) findViewById(R.id.edPasswordConfim);
        this.etPassword = (EditText) findViewById(R.id.edPassword);
        this.etAuthCode = (EditText) findViewById(R.id.etAuthCode);
        this.etPhoneNum = (EditText) findViewById(R.id.edPhoneNum);

        btnGetCode = (RoundTextView) findViewById(R.id.btnGetCode);
        btnGetCode.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        showTitleBack();

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btnNext:
                toNext();
                break;
            case R.id.btnGetCode:
                doGetCodeAction();
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void doGetCodeAction() {
        String phone = etPhoneNum.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(SignActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.isDigitsOnly(phone) || !phone.startsWith("1") || phone.length() != 11) {
            Toast.makeText(SignActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        progresser.showProgress();
        hideKeyboard();
        MsgCodeReq req = new MsgCodeReq();
        req.setMobileNo(phone);
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                MsgCodeResp resp = JSONObject.parseObject(response.getResult().toString(), MsgCodeResp.class);
                if (resp.getWarning() == 1 || resp.getWarning() == 2) {
                    Toast.makeText(SignActivity.this, resp.getHint(), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    btnGetCode.setEnabled(false);
                    handler.post(runnable);
                    Toast.makeText(SignActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFaild(Response response) {
                progresser.showContent();
                Tools.toastMsg(SignActivity.this, response.getMsg());
            }
        });
    }

    private void toNext() {
        String phone = etPhoneNum.getText().toString();
        String authCode = etAuthCode.getText().toString();
        String passwd = etPassword.getText().toString();
        String passwdConfim = etPasswordConfim.getText().toString();

        if (
                TextUtils.isEmpty(phone) ||
                        TextUtils.isEmpty(authCode) ||
                        TextUtils.isEmpty(passwd) ||
                        TextUtils.isEmpty(passwdConfim)
                ) {
            Tools.toastMsg(SignActivity.this, "请将信息填写完整!");
            return;
        }

        authCode = authCode.trim();
        passwd = passwd.trim();
        passwdConfim = passwdConfim.trim();
        if (
                TextUtils.isEmpty(phone) ||
                        TextUtils.isEmpty(authCode) ||
                        TextUtils.isEmpty(passwd) ||
                        TextUtils.isEmpty(passwdConfim)
                ) {
            Tools.toastMsg(SignActivity.this, "请将信息填写完整!");
            return;
        }

        if (!passwd.equals(passwdConfim)) {
            Tools.toastMsg(SignActivity.this, "两次输入的密码不一致!");
            return;
        }

        if (passwd.length() < Constants.PASSWORD_MIN_LENGTH) {
            Toast.makeText(SignActivity.this, "密码长度不得小于" + Constants.PASSWORD_MIN_LENGTH + "位", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterReq req = (RegisterReq) getIntent().getSerializableExtra("req");
        req.setCaptcha(authCode);
        req.setMobileNo(phone);
        req.setPasswd(Tools.sha(passwd));
        progresser.showProgress();
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                signFinish();
            }

            @Override
            public void onFaild(Response response) {
                progresser.showContent();
                Tools.toastMsg(SignActivity.this, response.getMsg());
            }
        });

    }

    public void signFinish() {
        Tools.toastMsg(SignActivity.this, "注册成功");
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(SignStep2Activity.SIGN_FINISH));
        Tools.toActivity(SignActivity.this, LoginActivity.class);
        this.finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            handler.removeCallbacks(runnable);
        } catch (Exception e) {
        }
    }
}
