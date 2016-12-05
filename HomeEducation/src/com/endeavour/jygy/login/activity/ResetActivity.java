package com.endeavour.jygy.login.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Constants;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.login.activity.bean.ResetMsgCodeReq;
import com.endeavour.jygy.login.activity.bean.ResetPasswdReq;
import com.flyco.roundview.RoundTextView;

public class ResetActivity extends BaseViewActivity {

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
        setTitleText("忘记密码");
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
                submit();
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
            Toast.makeText(ResetActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!phone.startsWith("1") || phone.length() != 11) {
            Toast.makeText(ResetActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }

        progresser.showProgress();
        ResetMsgCodeReq req = new ResetMsgCodeReq();
        req.setMobileNo(phone);
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                btnGetCode.setEnabled(false);
                handler.post(runnable);
                Toast.makeText(ResetActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Response response) {
                progresser.showContent();
                Tools.toastMsg(ResetActivity.this, response.getMsg());
            }
        });
    }

    private void submit() {
        hideKeyboard();
        String phone = etPhoneNum.getText().toString();
        String authCode = etAuthCode.getText().toString();
        String passwd = etPassword.getText().toString();
        String passwdConfim = etPasswordConfim.getText().toString();

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(authCode) || TextUtils.isEmpty(passwd) || TextUtils.isEmpty(passwdConfim)) {
            Tools.toastMsg(ResetActivity.this, "请将信息填写完整!");
            return;
        }
        if (!passwd.equals(passwdConfim)) {
            Tools.toastMsg(ResetActivity.this, "两次输入的密码不一致!");
            return;
        }

        if (passwd.length() < Constants.PASSWORD_MIN_LENGTH) {
            Toast.makeText(ResetActivity.this, "密码长度不得小于" + Constants.PASSWORD_MIN_LENGTH + "位", Toast.LENGTH_SHORT).show();
            return;
        }
        ResetPasswdReq req = new ResetPasswdReq();
        req.setCaptcha(authCode);
        req.setMobileNo(phone);
        req.setNewPasswd(Tools.sha(passwd));
        progresser.showProgress();
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                ResetActivity.this.finish();

            }

            @Override
            public void onFailed(Response response) {
                progresser.showContent();
                Tools.toastMsg(ResetActivity.this, response.getMsg());
            }
        });

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
