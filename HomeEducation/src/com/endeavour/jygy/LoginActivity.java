package com.endeavour.jygy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.common.Constants;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.db.DbHelper;
import com.endeavour.jygy.common.db.DefaultAppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.login.activity.IPSettingActivity;
import com.endeavour.jygy.login.activity.ResetActivity;
import com.endeavour.jygy.login.activity.SignStep2Activity;
import com.endeavour.jygy.login.activity.bean.Device;
import com.endeavour.jygy.login.activity.bean.LoginReq;
import com.endeavour.jygy.login.activity.bean.LoginResp;
import com.endeavour.jygy.login.activity.bean.UserInfo;
import com.endeavour.jygy.parent.activity.ChooseBabyActivity;
import com.endeavour.jygy.parent.activity.ParentHomeActivity;
import com.endeavour.jygy.schoolboss.activity.SchoolBossMainActivity;
import com.endeavour.jygy.teacher.activity.TeacherMainActivity;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.roundview.RoundTextView;
import com.qihoo.updatesdk.lib.UpdataHelper;

public class LoginActivity extends BaseViewActivity {

    private android.widget.EditText etPhoneNum;
    private android.widget.EditText etPassword;
    private android.widget.TextView tvForgotPassword;
    private com.flyco.roundview.RoundTextView btnLogin, btnSign;
    private ImageView ivUserIcon;
    private CheckBox ckLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_login);
        setTitleText("儒灵童好习惯");
        checkUpdate();//检查更新
        this.btnLogin = (RoundTextView) findViewById(R.id.btnPay);
        this.tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        this.etPassword = (EditText) findViewById(R.id.edPassword);
        this.etPhoneNum = (EditText) findViewById(R.id.etPhoneNum);
        this.btnSign = (RoundTextView) findViewById(R.id.btn2Sign);
        this.ckLogin = (CheckBox) findViewById(R.id.login_chk);
        this.btnLogin.setOnClickListener(this);
        this.btnSign.setOnClickListener(this);
        this.tvForgotPassword.setOnClickListener(this);
        ivUserIcon = (ImageView) findViewById(R.id.ivUserIcon);
        if (Constants.TRUE.equals(DefaultAppConfigHelper.getConfig(AppConfigDef.saveLogin))) {
            etPhoneNum.setText(DefaultAppConfigHelper.getConfig(AppConfigDef.saveLoginName));
            etPassword.setText(DefaultAppConfigHelper.getConfig(AppConfigDef.savePasswd));
            ckLogin.performClick();
        }
        if (BuildConfig.DEBUG) {
            ivUserIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTargetDialog();
                }
            });
            findViewById(R.id.btnIPSet).setVisibility(View.VISIBLE);
            findViewById(R.id.btnIPSet).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Tools.toActivity(LoginActivity.this, IPSettingActivity.class);
                            LoginActivity.this.finish();
                        }
                    });
        }
        if (Constants.TRUE.equals(DefaultAppConfigHelper
                .getConfig(AppConfigDef.isLogin))
                && !TextUtils.isEmpty(DefaultAppConfigHelper
                .getConfig(AppConfigDef.saveLoginName))
                && !TextUtils.isEmpty(DefaultAppConfigHelper
                .getConfig(AppConfigDef.savePasswd))) {
            // login();
            String saveLoginID = DefaultAppConfigHelper
                    .getConfig(AppConfigDef.dbName_saveLoginID);
            DbHelper.getInstance().init(MainApp.getInstance(), saveLoginID);
            String roleKey = AppConfigHelper.getConfig(AppConfigDef.roleKey);
            if (MainApp.isParent()) {
                if (!TextUtils.isEmpty(AppConfigHelper.getConfig(AppConfigDef.studentID))) {
                    toMainView(roleKey, false);
                }
            } else {
                toMainView(roleKey, false);
            }
        }

    }

    /**
     * 检查更新
     */
    private void checkUpdate() {
        UpdataHelper.getInstance().init(getApplication(), Color.parseColor("#0A93DB"));
//		UpdataHelper.getInstance().setDebugMode(true);
        UpdataHelper.getInstance().autoUpdate(getPackageName());//没界面
//		UpdataHelper.getInstance().manualUpdate(getPackageName());//有界面
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btnPay:
                login();
                break;
            case R.id.tvForgotPassword:
                toForgotPassword();
                break;
            case R.id.btn2Sign:
                Tools.toActivity(LoginActivity.this, SignStep2Activity.class);
                LoginActivity.this.finish();
                break;
        }
    }

    private void toForgotPassword() {
        // 忘记密码
        Tools.toActivity(LoginActivity.this, ResetActivity.class);
    }

    private void login() {
        final String phoneNum = etPhoneNum.getText().toString();
        final String password = etPassword.getText().toString();
        if (ckLogin.isChecked()) {
            DefaultAppConfigHelper.setConfig(AppConfigDef.saveLogin,
                    Constants.TRUE);
        } else {
            DefaultAppConfigHelper.setConfig(AppConfigDef.saveLogin,
                    Constants.FALSE);
        }
        DefaultAppConfigHelper.setConfig(AppConfigDef.saveLoginName, phoneNum);
        DefaultAppConfigHelper.setConfig(AppConfigDef.savePasswd, password);
        if (TextUtils.isEmpty(phoneNum)) {
            Toast.makeText(LoginActivity.this, "请输入手机号", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        if (phoneNum.length() != 11 || !phoneNum.startsWith("1")) {
            Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        if (password.length() < Constants.PASSWORD_MIN_LENGTH) {
            Toast.makeText(LoginActivity.this,
                    "密码长度不得小于" + Constants.PASSWORD_MIN_LENGTH + "位",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        hideKeyboard();
        Device device = new Device();
        device.setDeviceToken("android");
        device.setImei(Tools.getIMEI(this));
        device.setImsi(Tools.getIMSI(this));
        device.setMac(Tools.getIMEI(this));
        device.setVersion(Tools.getSDKVersion());
        device.setMobileType(Tools.getMobileName());
        LoginReq req = new LoginReq();
        req.setPasswd(Tools.sha(password.trim()));
        req.setMobileNo(phoneNum.trim());
        req.setDevice(device);
        progresser.showProgress();
        NetRequest.getInstance().addRequest(req,
                new BaseRequest.ResponseListener() {
                    @Override
                    public void onSuccess(Response response) {
                        progresser.showContent();
                        LoginResp resp = JSONObject.parseObject(response
                                .getResult().toString(), LoginResp.class);
                        UserInfo userInfo = resp.getUserInfo();
                        userInfo.setPhoneNum(phoneNum.trim());
                        // 初始化数据库
                        String dbName = userInfo.getUserId();
                        DefaultAppConfigHelper.setConfig(AppConfigDef.isLogin,
                                Constants.TRUE);// 记录登录状态
                        DefaultAppConfigHelper.setConfig(
                                AppConfigDef.isbabyUi, "0");//是否进入主界面
                        DefaultAppConfigHelper.setConfig(
                                AppConfigDef.dbName_saveLoginID, dbName);// 记录登录状态
                        DbHelper.getInstance().init(MainApp.getInstance(),
                                dbName);
                        AppConfigHelper.setConfig(AppConfigDef.loginResp,
                                response.getResult().toString());// 记录登录返回
                        AppConfigHelper.setConfig(AppConfigDef.phoneNum, phoneNum);
                        MainApp.getInstance().updateParentInfo(userInfo);
                        // if
                        // (!userInfo.getUserId().equals(AppConfigHelper.getConfig(AppConfigDef.parentId)))
                        // {
                        // MainApp.getInstance().resetApp();
                        // }

                        String roleKey = userInfo.getRoleKey();
                        toMainView(roleKey, true);
                    }

                    @Override
                    public void onFaild(Response response) {
                        progresser.showContent();
                        Tools.toastMsg(LoginActivity.this, response.getMsg());
                    }
                });
    }

    private void toMainView(String roleKey, boolean chooseBaby) {
        if (TextUtils.isEmpty(roleKey)) {
            progresser.showContent();
            Tools.toastMsg(LoginActivity.this, "权限错误!请联系管理人员");
            return;
        }
        if (roleKey.contains(UserInfo.ROLE_LEADER_ADMIN)) { // 园长
            toSchoolBossMainView(); // 园长端
            AppConfigHelper.setConfig(AppConfigDef.graduationFlag, "0");
        } else if (roleKey.contains(UserInfo.ROLE_ADMIN)) { // 超管
            progresser.showContent();
            Tools.toastMsg(LoginActivity.this, "权限错误!请联系管理人员");
        } else if (roleKey.contains(UserInfo.ROLE_RESTRICTED_ADMIN)) { //平台运维
            progresser.showContent();
            Tools.toastMsg(LoginActivity.this, "权限错误!请联系管理人员");
        } else if (roleKey.contains(UserInfo.ROLE_TEACHER)) { // 老师
            toTeacherMainView(); // 教师端
            AppConfigHelper.setConfig(AppConfigDef.graduationFlag, "0");
        } else if (roleKey.contains(UserInfo.ROLE_USER)) { // 家长
            toParentMainView(chooseBaby); // 家长端
        } else {
            progresser.showContent();
            Tools.toastMsg(LoginActivity.this, "权限错误!请联系管理人员");
        }
    }

    private void showTargetDialog() {
        final String[] stringItems = {"家长端", "教师端", "园长端"};
        final ActionSheetDialog dialog = new ActionSheetDialog(this,
                stringItems, findViewById(R.id.rlMain));
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                switch (position) {
                    case 0:
                        etPhoneNum.setText("18552034405");
                        etPassword.setText("111111");
                        break;
                    case 1:
                        etPhoneNum.setText("13771573332");
                        etPassword.setText("780119");
//                        etPhoneNum.setText("18115750801");
//                        etPassword.setText("111111");
                        break;
                    case 2:
                        etPhoneNum.setText("15852791651");
                        etPassword.setText("888888");
                        break;
                }
                dialog.dismiss();
            }
        });
    }

    private void toParentMainView(boolean chooseBaby) {
        if (chooseBaby) {
            Intent intent = new Intent(LoginActivity.this, ChooseBabyActivity.class);
            startActivityForResult(intent, 1001);
        } else {
            Tools.toActivity(LoginActivity.this, ParentHomeActivity.class);
            this.finish();
        }
    }

    private void toTeacherMainView() {
        Tools.toActivity(LoginActivity.this, TeacherMainActivity.class);
        this.finish();
    }

    private void toSchoolBossMainView() {
        Tools.toActivity(LoginActivity.this, SchoolBossMainActivity.class);
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            this.finish();
        }
    }
}
