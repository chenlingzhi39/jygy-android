package com.endeavour.jygy.login.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.OptionsPickerView;
import com.endeavour.jygy.LoginActivity;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.login.activity.bean.Device;
import com.endeavour.jygy.login.activity.bean.GetCitysReq;
import com.endeavour.jygy.login.activity.bean.GetCitysResp;
import com.endeavour.jygy.login.activity.bean.GetSchoolByLocationReq;
import com.endeavour.jygy.login.activity.bean.GetSchoolByLocationResp;
import com.endeavour.jygy.login.activity.bean.RegisterReq;
import com.flyco.roundview.RoundTextView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;

public class SignStep2Activity extends BaseViewActivity {
    private final int[] ARRAY_CITY = new int[]{R.array.beijin_province_item,
            R.array.tianjin_province_item, R.array.heibei_province_item,
            R.array.shanxi1_province_item, R.array.neimenggu_province_item,
            R.array.liaoning_province_item, R.array.jilin_province_item,
            R.array.heilongjiang_province_item, R.array.shanghai_province_item,
            R.array.jiangsu_province_item, R.array.zhejiang_province_item,
            R.array.anhui_province_item, R.array.fujian_province_item,
            R.array.jiangxi_province_item, R.array.shandong_province_item,
            R.array.henan_province_item, R.array.hubei_province_item,
            R.array.hunan_province_item, R.array.guangdong_province_item,
            R.array.guangxi_province_item, R.array.hainan_province_item,
            R.array.chongqing_province_item, R.array.sichuan_province_item,
            R.array.guizhou_province_item, R.array.yunnan_province_item,
            R.array.xizang_province_item, R.array.shanxi2_province_item,
            R.array.gansu_province_item, R.array.qinghai_province_item,
            R.array.ningxia_province_item, R.array.xinjiang_province_item,
            R.array.hongkong_province_item, R.array.aomen_province_item,
            R.array.taiwan_province_item};

    private android.widget.EditText etProvince;
    private android.widget.EditText etCity;
    private android.widget.EditText etArea;
    private android.widget.EditText etSchool;
    private android.widget.EditText etParentName;
    private android.widget.EditText etIdentity;
    private com.flyco.roundview.RoundTextView btnNextStep;

    private ArrayList<String> optsProvince;
    private List<GetCitysResp> provinces;
    private ArrayList<String> optsCity = new ArrayList<String>();
    private List<GetCitysResp> citys;
    private ArrayList<String> optsArea = new ArrayList<String>();
    private ArrayList<String> optsSchool = new ArrayList<String>();

    private List<GetSchoolByLocationResp> shools = new ArrayList<>();

    private OptionsPickerView pwProvince, pwCity, pwArea, pwSchool, pwIdentity;

    private ArrayList<String> optsRoles = new ArrayList<>();
    private String selectedSchoolId;

    public static final String SIGN_FINISH = "com.endeavour.jygy.login.activity.SignActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_sign_step2);
        setTitleText("注册");

        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SignStep2Activity.this.finish();
            }
        }, new IntentFilter(SIGN_FINISH));

        this.btnNextStep = (RoundTextView) findViewById(R.id.btnNextStep);
        this.etIdentity = (EditText) findViewById(R.id.etIdentity);
        this.etParentName = (EditText) findViewById(R.id.etParentName);
        this.etSchool = (EditText) findViewById(R.id.etSchool);
        this.etArea = (EditText) findViewById(R.id.etArea);
        this.etCity = (EditText) findViewById(R.id.etCity);
        this.etProvince = (EditText) findViewById(R.id.etProvince);
        //1 父亲 2母亲 3 长辈 4 保姆 5其他
        optsRoles.add("父亲");
        optsRoles.add("母亲");
        optsRoles.add("长辈");
        optsRoles.add("保姆");
        optsRoles.add("其他");

//        String[] temp = (getResources().getStringArray(R.array.province_item));
//        optsProvince = Tools.trans(temp);

        getProvinces(new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                provinces = JSONArray.parseArray(String.valueOf(response.getResult()), GetCitysResp.class);
                optsProvince = handleCitys2Strings(provinces);
                if (optsProvince == null) {
                    Tools.toastMsg(SignStep2Activity.this, "没有找到对应的省份");
                    return;
                }
                initView();
            }

            @Override
            public void onFaild(Response response) {
                progresser.showContent();
                Tools.toastMsg(SignStep2Activity.this, response.getMsg());
            }
        });

    }

    private void initView() {
        pwProvince = new OptionsPickerView(this);
        pwProvince.setPicker(optsProvince);
        pwProvince.setLabels("省");
        pwProvince.setCyclic(false);
        pwProvince.setSelectOptions(0);
        //监听确定选择按钮
        pwProvince.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = optsProvince.get(options1);
                etProvince.setText(tx);
                etCity.setText("");
                etProvince.setTag(options1);
                citys = null;
                optsCity = null;
                progresser.showProgress();
                getCityList(provinces.get(options1).getId() + "", new BaseRequest.ResponseListener() {
                    @Override
                    public void onSuccess(Response response) {
                        progresser.showContent();
                        citys = JSONArray.parseArray(String.valueOf(response.getResult()), GetCitysResp.class);
                        optsCity = handleCitys2Strings(citys);
                        if (optsCity == null) {
                            Tools.toastMsg(SignStep2Activity.this, "没有找到对应的城市");
                            return;
                        }
                        pwCity.setPicker(optsCity);
                    }

                    @Override
                    public void onFaild(Response response) {
                        progresser.showContent();
                        Tools.toastMsg(SignStep2Activity.this, response.getMsg());
                    }
                });
//                String[] temp = getResources().getStringArray(ARRAY_CITY[options1]);
//                optsCity = Tools.trans(temp);

            }
        });

        pwCity = new OptionsPickerView(this);
        pwCity.setCyclic(false);
        pwCity.setPicker(optsCity);
        pwCity.setLabels("市");
        pwCity.setSelectOptions(0);
        //监听确定选择按钮
        pwCity.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = optsCity.get(options1);
                etCity.setText(tx);
                etCity.setTag(options1);
            }
        });

        pwArea = new OptionsPickerView(this);
        pwArea.setPicker(optsArea);
        pwArea.setLabels("区");
        pwArea.setCyclic(false);
        pwArea.setSelectOptions(0);
        //监听确定选择按钮
        pwArea.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = optsArea.get(options1);
                etArea.setText(tx);
            }
        });

        pwSchool = new OptionsPickerView(this);
        pwSchool.setPicker(optsSchool);
        pwSchool.setLabels("");
        pwSchool.setCyclic(false);
        pwSchool.setSelectOptions(0);
        //监听确定选择按钮
        pwSchool.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = optsSchool.get(options1);
                etSchool.setText(tx);
                selectedSchoolId = shools.get(options1).getId();
                Log.d("selectedSchoolId", selectedSchoolId + "");
            }
        });
        pwIdentity = new OptionsPickerView(this);
        pwIdentity.setPicker(optsRoles);
        pwIdentity.setCyclic(false);
        pwIdentity.setLabels("角色");
        pwIdentity.setSelectOptions(0);
        //监听确定选择按钮
        pwIdentity.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = optsRoles.get(options1);
                etIdentity.setText(tx);
            }
        });

        etProvince.setOnClickListener(this);
        etCity.setOnClickListener(this);
        etSchool.setOnClickListener(this);
        etIdentity.setOnClickListener(this);
        this.btnNextStep.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Tools.hideKeyBorad(this, etParentName);
    }

    private void getSchoolList() {
        GetSchoolByLocationReq req = new GetSchoolByLocationReq();
        String city = etCity.getText().toString();
        String province = etProvince.getText().toString();
        if (TextUtils.isEmpty(city) || TextUtils.isEmpty(province)) {
            Tools.toastMsg(this, "请先选择省和市");
            return;
        }
        progresser.showProgress();
        try {
            int cityCode = Integer.parseInt(String.valueOf(etCity.getTag()));
            int provinceCode = Integer.parseInt(String.valueOf(etProvince.getTag()));
            req.setCity(citys.get(cityCode).getId() + "");
            req.setProvince(provinces.get(provinceCode).getId() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                shools.clear();
                optsSchool.clear();
                shools = JSONObject.parseArray(response.getResult().toString(), GetSchoolByLocationResp.class);
                if (shools == null || shools.isEmpty()) {
                    Tools.toastMsg(SignStep2Activity.this, "没有找到对应的幼儿园");
                    return;
                }
                int i = 0;
                for (GetSchoolByLocationResp resp : shools) {
                    if (i == 0) {
                        selectedSchoolId = resp.getId();
                    }
                    optsSchool.add(resp.getName());
                    i++;
                }

                pwSchool.setPicker(optsSchool);
                pwSchool.show();
            }

            @Override
            public void onFaild(Response response) {
                progresser.showContent();
                Tools.toastMsg(SignStep2Activity.this, response.getMsg());
            }
        });
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btnNextStep:
                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        toNext();
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Toast.makeText(SignStep2Activity.this, "权限拒绝, 请开启权限", Toast.LENGTH_SHORT).show();
                    }

                };
                new TedPermission(this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("请开启权限")
                        .setPermissions(Manifest.permission.READ_PHONE_STATE)
                        .check();
                break;
            case R.id.etProvince:
                Tools.hideKeyBorad(this, getCurrentFocus());
                pwProvince.show();
                break;
            case R.id.etCity:
                Tools.hideKeyBorad(this, getCurrentFocus());
                String province = etProvince.getText().toString();
                if (TextUtils.isEmpty(province)) {
                    Tools.toastMsg(this, "请先选择省");
                    return;
                }
                if(optsCity == null || optsCity.isEmpty()){
                    Tools.toastMsg(SignStep2Activity.this, "没有找到对应的城市");
                    return;
                }
                pwCity.show();
                break;
            case R.id.etIdentity:
                pwIdentity.show();
                break;
            case R.id.etSchool:
                getSchoolList();
                break;
        }
    }

    private void toNext() {
        if(TextUtils.isEmpty(selectedSchoolId)){
            Toast.makeText(SignStep2Activity.this, "请将信息补充完整", Toast.LENGTH_SHORT).show();
        }
        String province = etProvince.getText().toString();
        String city = etCity.getText().toString();
        String school = etSchool.getText().toString();
        String parentName = etParentName.getText().toString();
        String identity = etIdentity.getText().toString();
        if (TextUtils.isEmpty(province) ||
                TextUtils.isEmpty(city) ||
                TextUtils.isEmpty(school) ||
                TextUtils.isEmpty(parentName) ||
                TextUtils.isEmpty(identity)
                ) {
            Tools.toastMsg(SignStep2Activity.this, "请将信息补充完整");
            return;
        }

        parentName = parentName.trim();

        if (TextUtils.isEmpty(province) ||
                TextUtils.isEmpty(city) ||
                TextUtils.isEmpty(school) ||
                TextUtils.isEmpty(parentName) ||
                TextUtils.isEmpty(identity)
                ) {
            Tools.toastMsg(SignStep2Activity.this, "请将信息补充完整");
            return;
        }

        Device device = new Device();
        device.setDeviceToken("android");
        device.setImei(Tools.getIMEI(this));
        device.setImsi(Tools.getIMSI(this));
        device.setMac(Tools.getIMEI(this));
        device.setVersion(Tools.getSDKVersion());
        device.setMobileType(Tools.getMobileName());
        RegisterReq req = new RegisterReq();
        req.setProvince(province);
        req.setCity(city);
        req.setUserName(parentName);
        req.setDevice(device);
        req.setProtectRole(getRolesId(identity));
        req.setKindergartenId(Integer.parseInt(selectedSchoolId));
        Intent intent = new Intent(this, SignActivity.class);
        intent.putExtra("req", req);
        startActivity(intent);

    }

    public String getRolesId(String role) {
        for (int i = 0; i < optsRoles.size(); i++) {
            if (optsRoles.get(i).equals(role)) {
                return (i + 1) + "";
            }
        }
        return "0";
    }

    private void getProvinces(BaseRequest.ResponseListener listener) {
        getCityList("100000", listener);
    }

    public void getCityList(String parentId, BaseRequest.ResponseListener listener) {
        GetCitysReq req = new GetCitysReq();
        req.setParentId(parentId);
        NetRequest.getInstance().addRequest(req, listener);
    }

    private ArrayList<String> handleCitys2Strings(List<GetCitysResp> citys) {
        if (citys == null || citys.isEmpty()) {
            return null;
        }
        ArrayList<String> cityStrings = new ArrayList<>();
        for (GetCitysResp resp : citys) {
            cityStrings.add(resp.getShortName());
        }
        return cityStrings;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Tools.toActivity(this, LoginActivity.class);
    }
}
