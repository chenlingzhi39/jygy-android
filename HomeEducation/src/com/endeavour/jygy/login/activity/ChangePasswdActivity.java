package com.endeavour.jygy.login.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Constants;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.login.activity.bean.ChangePasswdReq;
import com.flyco.roundview.RoundTextView;

public class ChangePasswdActivity extends BaseViewActivity {

    private EditText etOldPasswd;
    private EditText etPassword;
    private EditText etPasswordConfim;
    private RoundTextView btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_change_passwd);
        setTitleText("修改密码");
        showTitleBack();
        this.btnNext = (RoundTextView) findViewById(R.id.btnNext);
        this.etPasswordConfim = (EditText) findViewById(R.id.edPasswordConfim);
        this.etPassword = (EditText) findViewById(R.id.etNewPassword);
        this.etOldPasswd = (EditText) findViewById(R.id.etOldPasswd);

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
        }
    }

    private void submit() {
        hideKeyboard();
        String oldPasswd = etOldPasswd.getText().toString();
        String passwd = etPassword.getText().toString();
        String passwdConfim = etPasswordConfim.getText().toString();

        if (TextUtils.isEmpty(oldPasswd) || TextUtils.isEmpty(passwdConfim) || TextUtils.isEmpty(passwd) || TextUtils.isEmpty(passwdConfim)) {
            Tools.toastMsg(ChangePasswdActivity.this, "请将信息填写完整!");
            return;
        }
        if (passwd.length() < Constants.PASSWORD_MIN_LENGTH) {
            Toast.makeText(ChangePasswdActivity.this, "密码长度不得小于" + Constants.PASSWORD_MIN_LENGTH + "位",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (!passwd.equals(passwdConfim)) {
            Tools.toastMsg(this, "两次输入的密码不一致!");
            return;
        }

        ChangePasswdReq req = new ChangePasswdReq();
        req.setUserId(AppConfigHelper.getConfig(AppConfigDef.parentId));
        req.setOldPasswd(Tools.sha(oldPasswd));
        req.setNewPasswd(Tools.sha(passwd));
        progresser.showProgress();
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                Tools.toastMsg(ChangePasswdActivity.this, "密码修改成功");
                ChangePasswdActivity.this.finish();
            }

            @Override
            public void onFaild(Response response) {
                progresser.showContent();
                Tools.toastMsg(ChangePasswdActivity.this, response.getMsg());
            }
        });

    }

}
