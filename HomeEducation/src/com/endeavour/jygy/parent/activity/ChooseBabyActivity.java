package com.endeavour.jygy.parent.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.db.DefaultAppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.login.activity.bean.Device;
import com.endeavour.jygy.login.activity.bean.LoginReq;
import com.endeavour.jygy.login.activity.bean.LoginResp;
import com.endeavour.jygy.login.activity.bean.UserInfo;
import com.endeavour.jygy.parent.adapter.ChooseBabyAdapter;
import com.endeavour.jygy.parent.bean.GetStudentVerityInfoReq;
import com.endeavour.jygy.parent.bean.GetStudentVerityInfoResp;
import com.endeavour.jygy.parent.bean.Student;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.wizarpos.log.util.LogEx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/11/27.
 */
public class ChooseBabyActivity extends BaseViewActivity implements ChooseBabyAdapter.OnBabySelectedListener {

    private List<Student> babyInfos;
    private ViewPager vpChooseBaby;
    private TextView tvLeft, tvRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_baby);
        vpChooseBaby = (ViewPager) findViewById(R.id.vpChooseBaby);
        tvLeft = (TextView) findViewById(R.id.tvLeft);
        tvRight = (TextView) findViewById(R.id.tvRight);
        findViewById(R.id.rlAddBaby).setOnClickListener(this);
        tvRight.setOnClickListener(this);
        tvLeft.setOnClickListener(this);
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                initData();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(ChooseBabyActivity.this, "权限拒绝, 请开启权限", Toast.LENGTH_SHORT).show();
            }

        };
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("请开启权限")
                .setPermissions(Manifest.permission.READ_PHONE_STATE)
                .check();
    }

    private void initData() {
        String loginResp = AppConfigHelper.getConfig(AppConfigDef.loginResp);
        LogEx.d("loginResp", loginResp);
        LoginResp resp = JSONObject.parseObject(loginResp, LoginResp.class);
        babyInfos = resp.getChildInfo();
        if (babyInfos == null || babyInfos.isEmpty()) { //没有宝宝,添加宝宝
            Intent intent = new Intent(ChooseBabyActivity.this, AddBabyTipsActivity.class);
            intent.putExtra(EditBabyActivity.SCHOOLID, resp.getUserInfo().getKinderId() + "");//学校ID
            intent.putExtra(EditBabyActivity.PARENT_NAME, resp.getUserInfo().getUserName());//家长姓名
            startActivityForResult(intent, 10001);
//            this.finish();
            return;
        }
        String selectedBabyId = AppConfigHelper.getConfig(AppConfigDef.selectedBabyId);
        int selectedBabyPosition = 0;
        if (!TextUtils.isEmpty(selectedBabyId)) {
            for (int i = 0; i < babyInfos.size(); i++) {
                if (selectedBabyId.equals(babyInfos.get(i).getUserId())) {
                    selectedBabyPosition = i;
                    break;
                }
            }
        }
        ChooseBabyAdapter chooseBabyAdapter = new ChooseBabyAdapter(this, babyInfos, this);
        vpChooseBaby.setAdapter(chooseBabyAdapter);
        if (babyInfos.size() == 1) {
            tvRight.setVisibility(View.INVISIBLE);
            tvLeft.setVisibility(View.INVISIBLE);
        } else {
            tvRight.setVisibility(View.VISIBLE);
            tvLeft.setVisibility(View.INVISIBLE);
            vpChooseBaby.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    if (i == 0) {
                        tvLeft.setVisibility(View.INVISIBLE);
                        tvRight.setVisibility(View.VISIBLE);
                    } else if (i == babyInfos.size() - 1) {
                        tvLeft.setVisibility(View.VISIBLE);
                        tvRight.setVisibility(View.INVISIBLE);
                    } else {
                        tvLeft.setVisibility(View.VISIBLE);
                        tvRight.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        }
        vpChooseBaby.setCurrentItem(selectedBabyPosition);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rlAddBaby://添加宝宝
                Intent intent = new Intent(this, EditBabyActivity.class);
                startActivityForResult(intent, 10001);
//                this.finish();
                break;
            case R.id.tvLeft:
                vpChooseBaby.setCurrentItem(vpChooseBaby.getCurrentItem() - 1);
                break;
            case R.id.tvRight:
                vpChooseBaby.setCurrentItem(vpChooseBaby.getCurrentItem() + 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10001 && resultCode == RESULT_OK) {
            login();
        }
    }

    private void login() {
        Device device = new Device();
        device.setDeviceToken("android");
        device.setImei(Tools.getIMEI(this));
        device.setImsi(Tools.getIMSI(this));
        device.setMac(Tools.getIMEI(this));
        device.setVersion(Tools.getSDKVersion());
        device.setMobileType(Tools.getMobileName());
        LoginReq req = new LoginReq();
        req.setPasswd(Tools.sha(DefaultAppConfigHelper.getConfig(AppConfigDef.savePasswd)));
        req.setMobileNo(DefaultAppConfigHelper.getConfig(AppConfigDef.saveLoginName));
        req.setDevice(device);
        progresser.showProgress();
        NetRequest.getInstance().addRequest(req,
                new BaseRequest.ResponseListener() {
                    @Override
                    public void onSuccess(Response response) {
                        progresser.showContent();
                        LoginResp resp = JSONObject.parseObject(response.getResult().toString(), LoginResp.class);
                        UserInfo userInfo = resp.getUserInfo();
                        MainApp.getInstance().updateParentInfo(userInfo);
                        initData();
                    }

                    @Override
                    public void onFailed(Response response) {
                        progresser.showContent();
                        Tools.toastMsg(ChooseBabyActivity.this, response.getMsg());
                    }
                });
    }

    public void onBabySelected(Student studen) {
        getStudentVerityInfo(studen);
    }

    private void selectedBaby(Student studen) {
        AppConfigHelper.setConfig(AppConfigDef.selectedBabyId, studen.getUserId());
        AppConfigHelper.setConfig(AppConfigDef.babySex, studen.getSex());
        MainApp.getInstance().updateBabyInfo(studen);
        AppConfigHelper.setConfig(AppConfigDef.headPortrait, studen.getHeadPortrait());
        LogEx.d("update headPortrait", studen.getHeadPortrait());
        setResult(RESULT_OK);
        Tools.toActivity(ChooseBabyActivity.this, ParentHomeActivity.class);
        this.finish();
    }

    public void getStudentVerityInfo(final Student student) {
        progresser.showProgress();
        GetStudentVerityInfoReq req = new GetStudentVerityInfoReq();
        req.setStudentId(student.getUserId());
        req.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        req.setClassId(student.getClassId());
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                GetStudentVerityInfoResp resp = JSONObject.parseObject(String.valueOf(response.getResult()), GetStudentVerityInfoResp.class);
                if ("0".equals(resp.getValidFlag())) {
                    selectedBaby(student);
                } else {
                    progresser.showContent();
                    Tools.toastMsg(ChooseBabyActivity.this, "宝宝还没有通过审核, 请耐心等待");
                }
            }

            @Override
            public void onFailed(Response response) {
                progresser.showContent();
                Toast.makeText(ChooseBabyActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
